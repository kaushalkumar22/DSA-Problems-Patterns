package lld.filesystemdesign;


import java.util.*;

//================= NODE CLASSES =================
abstract class Node {
    String name;
    Node parent;
    Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
    }
}

class Directory extends Node {
    Map<String, Node> children;
    Directory(String name,Directory parent) {
       super(name, parent);
       children = new HashMap<>();
    }
}

class File extends Node{
    StringBuilder content;
    File(String name,Directory parent) {
        super(name,parent);
        content = new StringBuilder();
    }
}
// ================= FILE SYSTEM =================
class FileSystem {
    private final Directory root;

    public FileSystem() {
        root = new Directory("/", null);
    }

    // -------- mkdir -----------
    public void mkdir(String path) {
        String[] parts = path.split("/");
        Directory curr = root;

        for (int i = 1; i < parts.length; i++) {
            curr.children.putIfAbsent(parts[i], new Directory(parts[i], curr));
            curr = (Directory) curr.children.get(parts[i]);
        }
    }

    // -------- create file -----------
    public void createFile(String path) {
        String[] parts = path.split("/");
        Directory curr = root;
        for (int i = 1; i < parts.length-1; i++) {
            curr.children.putIfAbsent(parts[i], new Directory(parts[i], curr));
            curr = (Directory) curr.children.get(parts[i]);
        }
        String fileName = parts[parts.length-1];

        Directory dir = curr;
        if (dir.children.containsKey(fileName)) {
            throw new RuntimeException("File already exists");
        }
        dir.children.put(fileName, new File(fileName, dir));
    }

    // -------- write -----------
    public void write(String path, String content) {
        File file = (File) traverse(path);
        file.content.append(content);
    }

    // -------- read -----------
    public String read(String path) {
        File file = (File) traverse(path);
        return file.content.toString();
    }

    // -------- list directory -----------
    public List<String> ls(String path) {
        Node node = traverse(path);

        if (node instanceof File) {
            return List.of(node.name);
        }

        Directory dir = (Directory) node;
        return new ArrayList<>(dir.children.keySet());
    }

    // -------- delete -----------
    public void delete(String path) {
        if (path.equals("/")) {
            throw new RuntimeException("Cannot delete root");
        }

        Node node = traverse(path);
        Directory parent = (Directory) node.parent;

        parent.children.remove(node.name);
    }

    // -------- helper: traverse path -----------
    private Node traverse(String path) {
        if (path.equals("/")) return root;

        String[] parts = path.split("/");
        Node curr = root;

        for (int i = 1; i < parts.length; i++) {
            if (!(curr instanceof Directory)) {
                throw new RuntimeException("Invalid path");
            }

            Directory dir = (Directory) curr;
            curr = dir.children.get(parts[i]);

            if (curr == null) {
                throw new RuntimeException("Path not found: " + path);
            }
        }
        return curr;
    }
}


public class Demo {
    // ================= DEMO =================
    public static void main(String[] args) {

        FileSystem fs = new FileSystem();

        System.out.println("Creating directories...");
        fs.mkdir("/a");
        fs.mkdir("/a/b");
        fs.mkdir("/a/b/c");

        System.out.println("Creating file...");
        fs.createFile("/a/b/c/file.txt");

        System.out.println("Writing to file...");
        fs.write("/a/b/c/file.txt", "Hello ");
        fs.write("/a/b/c/file.txt", "World!");

        System.out.println("Reading file:");
        System.out.println(fs.read("/a/b/c/file.txt"));

        System.out.println("\nListing /a/b:");
        System.out.println(fs.ls("/a/b"));

        System.out.println("\nDeleting file...");
        fs.delete("/a/b/c/file.txt");

        System.out.println("Listing /a/b/c after deletion:");
        System.out.println(fs.ls("/a/b/c"));

        System.out.println("\nDemo Completed Successfully!");
    }
}