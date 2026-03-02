package code.graphs;
public class NumberOfIslands {

    public int maxAreaOfIsland(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        int maxArea =0;
        for(int i = 0 ;i<row ;i++){
            for(int j =0 ;j<col ;j++){
                if(grid[i][j] ==1){
                    maxArea++;
                    dfs(grid,i,j);

                }
            }
        }
        return maxArea;
    }
    private void  dfs(int[][] grid,int row,int col){
        if(row<0 || row >=grid.length || col<0 || col>= grid[0].length || grid[row][col]!=1){
            return;
        }
        grid[row][col]=0;
        dfs(grid,row -1,col);
        dfs(grid,row,col-1);
        dfs(grid,row+1,col);
        dfs(grid,row,col+1);
    }
}