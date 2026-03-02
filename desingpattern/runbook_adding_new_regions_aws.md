````md
# Runbook_adding_new_regions_aws.md

# Runbook: Expand HCP Vault Dedicated (HVD) to New AWS Regions

## 1. Pre-Execution Checklist
- Verify AWS Region Availability  
- Action for Disabled Regions  

## 2. Detailed Execution Steps
- Overview: Execution Phases  

## 3. Phase 1: Data Plane (DP)
- cloud-provider  
  - Step 1: Resource Creation  
  - Optional: Supported Region Configuration Updates  
  - Step 2: Verify Created Resources  
- Deploy cloud-sre  
  - Sample Configuration  
  - UUID Generation  
  - AWS Account ID Mapping  
  - S3 Bucket Validation  
- Deploy cloud-vault-host-manager  
  - Required Workflow Changes  
  - Image Registration Verification (remote-dev)  
  - Mandatory Cleanup Before Merge  
- Deploy cloud-ui  

## 4. Phase 2: Disaster Recovery (DR)
- DR Account Overview  
- Step 1: Access the DR Account  
- Step 2: Create DR S3 Bucket  
- Step 3: Apply Required Bucket Settings  
- Step 4: Create Environment Objects  
- Step 5: Create Vault Folder Structure  
- Step 6: Map DR Resources to vault-service  

## 5. Final Step: Billing-Related Change
- Billing Coordination  
- Merge Sequence  

---

## 1. Pre-Execution Checklist

### 1. Verify AWS Region Availability
- Log in to the AWS Console using any environment account access.
- Confirm whether the required AWS regions are enabled for the account.

### 2. For example Target Regions are
- AWS Paris – eu-west-3 --> Enabled by default
- AWS Hong Kong (ap-east-1) --> Disabled

### 3. Action for Disabled Regions
If a required region is disabled, raise a ticket to request region enablement.  
Ref: https://hashicorp.atlassian.net/browse/SECS-6807

---

## 2. Detailed Execution Steps

We create resources in two phases:
- a. Data Plane (DP)
- b. Disaster Recovery (DR)

---

## 3. Phase 1: Data Plane (DP)

Data Plane resources are created via the pipeline after merging the PR in cloud-provider.
- Run Terraform apply
- Ensure infrastructure and required paths are available

### 3.1 cloud-provider

#### Step 1: Resource Creation
This step creates the core cloud infrastructure, such as:
- Blob storage
- Base IAM / roles
- Foundational cloud resources

PR: https://github.com/hashicorp/cloud-provider/pull/1044  

NOTE: Terraform apply must run here.

Change Steps in change Steps in cloud-provide

#### Optional (Recommended) Configuration Update
Although not mandatory, it is good practice to update the supported regions documentation.
- Currently supported production regions are documented in:  
  adding_new_regions.md

For the following files changes are simple, either copy an existing region configuration and update the name, or add the new region to the existing list.

Files to update:
- cloud-provider/terraform/prde/_config.tf
- cloud-provider/terraform/remote-geography/_config.tf
- cloud-provider/terraform/prde/blob-buckets.tf
- cloud-provider/terraform/remote-geography/blob-buckets.tf
- cloud-provider/terraform/prde/roles.tf
- cloud-provider/terraform/remote-geography/roles.tf
- cloud-provider/validation/aws/rule_region.go

#### Step 2: Verify Created Resources
- Initially, resources can be verified from the release pipeline after the PR is merged.
- It is strongly recommended to:
  - Verify all newly created resources directly in the AWS Console
  - Capture screenshots/snapshots of the resources

These snapshots will:
- Serve as proof for the upcoming PR in cloud-sre
- Provide accurate configuration details needed in the cloud-sre PR
- Help avoid assumptions — always derive values from actual resources

Note: Do not assume values when writing cloud-sre code. Always validate from the real resources created in DP.

Pipeline link:  
https://github.com/hashicorp/cloud-provider/actions/runs/21161944907/job/60858765782

We can verify all newly created resources from AWS consol in respective account in S3 buckets

Accounts are:
- Dev: 947034012906
- Int: 169855265084
- Prod: 402237480865

---

### 3.2 Deploy cloud-sre

This step configures cloud-sre using outputs generated from the cloud-provider Terraform apply.
- Uses paths and outputs from cloud-provider
- Standalone change — does not impact running environments
- Can be merged anytime after cloud-provider is merged
- PR: https://github.com/hashicorp/cloud-sre/pull/3638

Change Steps in cloud-sre

#### Sample Configuration to Add
```json
{
  "region": {
    "region": "eu-west-3",
    "provider": "aws"
  },
  "uuid": "0A07160C-5E89-4950-A938-8CA9181B9EA1",
  "state": 0,
  "provider_config": {
    "aws": {
      "role_arn": "arn:aws:iam::402237480865:role/us_hcp_data_plane_blob_access"
    },
    "vault_path": "provider/aws/-/data_plane_blob_storage"
  },
  "blob_buckets": [
    {
      "aws_s3_bucket": {
        "bucket": "us-data-plane-eu-west-3-blob20260120064910882000000001",
        "region": "eu-west-3"
      }
    }
  ]
}
````

#### A. UUID Generation

A randomized UUID is required to ensure global uniqueness, prevent name collisions, across environments allow safe re-applies.

How to generate (Mac / Linux):
Uuidgen

Example:

```json
"uuid": "08E24DC4-DF4B-4C80-A34D-C97F07FDD987"
```

#### B. AWS Account ID

The AWS Account ID remains the same across regions.
It differs per environment (dev, int, prod, remote-dev).

Example:

* Prod: 402237480865
* arn:aws:iam::402237480865:role/us_hcp_data_plane_blob_access

#### C. S3 Bucket Name

* Always fetch the exact bucket name from the AWS Console
* The bucket is created after cloud-provider PR is merged
* Do not assume or derive the name

Example:

```json
"bucket": "us-data-plane-eu-west-3-blob20260120064910882000000001"
```

Refer to the AWS Console (and attached screenshots) for the authoritative value in the respective environment.

---

### 3.3 Deploy cloud-vault-host-manager

This phase provisions and manages Vault hosts using pre-built image artifacts and storage paths.

* Uses image artifacts and storage paths to provision and manage hosts
* PR: [https://github.com/hashicorp/cloud-vault-host-manager/pull/1111](https://github.com/hashicorp/cloud-vault-host-manager/pull/1111)

#### Required Changes

The changes are straightforward — add the new region name to the following workflow files:

* .github/workflows/_build-reporting-image.yml
* .github/workflows/build-custom-vault-images.yml
* .github/workflows/custom-image-build.yaml
* .github/workflows/release.yaml

#### Verification: Image Registration

To verify that images are correctly registered, perform the following temporary change in `.github/workflows/test.yaml`.

NOTE: This step is for verification purposes only

Verification Steps:

1. Add the region (eu-west-3) and set noop: false
2. Run the workflow from the PR
3. Verify that images are successfully registered in remote-dev
4. Take screenshots of the successful image registration
5. Attach the screenshots to the PR as evidence
6. From AWS console this can be verified in EC2 not in S3

#### Before Merge (Mandatory Cleanup)

Revert the temporary verification changes:

* Remove eu-west-3 (if it was added only for verification)
* Set noop back to its original value

---

### 3.4 Deploy cloud-ui

* UI changes are straightforward and feature-flag, so can be deployed anytime.
* PR: [https://github.com/hashicorp/cloud-ui/pull/13053](https://github.com/hashicorp/cloud-ui/pull/13053)

---

## 4. Phase 2: Disaster Recovery (DR)

DR resources are created in a dedicated AWS account used only for Disaster Recovery.
Each environment has its own isolated setup.

DR AWS Account ID: 730335438126

### How to Create DR Resources

1. Access the DR Account
2. Create the DR S3 Bucket
3. Apply Required Bucket Settings
4. Create Environment Objects
5. Create Vault Folder
6. Mapping resources to vault-service

### Step 1: Access the DR Account

* Obtain developer access to AWS account 730335438126
* Log in to the AWS Console
* Navigate to S3

### Step 2: Create the DR S3 Bucket

Bucket naming standard (mandatory): `hcp-dr-<region>`

Example (eu-west-3):
hcp-dr-eu-west-3

Steps:

1. Create a new S3 bucket
2. Prefix must be `hcp-dr-`
3. Suffix must be the AWS region name

### Step 3: Apply Required Bucket Settings

* Configure the bucket settings by copying from an existing DR bucket
* Ensure settings (encryption, access policy, versioning, etc.) match existing DR standards

### Step 4: Create Environment Objects

Inside the bucket, create four top-level objects (folders):

* dev-remote
* dev
* int
* prod

### Step 5: Create Vault Folder

Inside each environment folder, create a vault directory:

* dev-remote/vault/
* dev/vault/
* int/vault/
* prod/vault/

Note: Folder structure must be consistent across all regions

### Step 6: Mapping dr resources to vault-service

PR: [https://github.com/hashicorp/cloud-vault-service/pull/5257](https://github.com/hashicorp/cloud-vault-service/pull/5257)

Once the DR S3 resources are created in the DR AWS account, they must be explicitly mapped in vault-service so Terraform can recognize and manage them.

Important:
If you directly add entries in Terraform before the resources exist, Terraform will fail with “resource not found / not available” errors.
Always create the DR resources first, then map them.

Files to Update (vault-service):

* internal/common/dr_helpers.go
* terraform/modules/dr_healthchecks_storage/main.tf
* terraform/remote/import.tf

Why This Order Matters:

* DR buckets are pre-existing, manually created resources
* Terraform in vault-service expects them to already exist
* Import-based management avoids:

    * Accidental recreation
    * Apply failures
    * Cross-account permission issues

---

## 5. Final Step: Billing-Related Change

The last and final change is related to billing, which is also handled as part of the following PR:
PR: [https://github.com/hashicorp/cloud-vault-service/pull/5257](https://github.com/hashicorp/cloud-vault-service/pull/5257)

### Billing Coordination

This change requires early coordination with the Billing team.

* Notify the Billing team at the start of this work
* Share the new region name and ensure they acknowledged
* Billing changes are dependent on vault-service updates

### Merge Sequence

1. Vault-service PR is reviewed and merged
2. Billing team merges their prepared changes immediately after that

```
```
