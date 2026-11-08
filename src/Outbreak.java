import java.util.*;
public class Outbreak {
    public static boolean[] cheapestTests(int n,int[][] nb,int[] cost) {
        long[][] dp=new long[n][2]; int[] parent=new int[n]; Arrays.fill(parent,-1);
        ArrayList<Integer> order=new ArrayList<>(); ArrayDeque<Integer> st=new ArrayDeque<>();
        st.push(0); parent[0]=0;
        while(!st.isEmpty()) { int v=st.pop(); order.add(v);
            for(int u:nb[v]) if(u!=parent[v]) { parent[u]=v; st.push(u); }
        }
        for(int z=order.size()-1;z>=0;z--) {
            int v=order.get(z); dp[v][1]=cost[v]; dp[v][0]=0;
            for(int u:nb[v]) if(parent[u]==v) {
                dp[v][1]+=Math.min(dp[u][0],dp[u][1]);
                dp[v][0]+=dp[u][1];
            }
        }
        boolean[] answer=new boolean[n]; ArrayDeque<int[]> todo=new ArrayDeque<>();
        todo.push(new int[]{0,dp[0][1]<=dp[0][0]?1:0});
        while(!todo.isEmpty()) {
            int[] x=todo.pop(); int v=x[0],state=x[1]; answer[v]=state==1;
            for(int u:nb[v]) if(parent[u]==v) {
                int child=state==0?1:(dp[u][1]<=dp[u][0]?1:0);
                todo.push(new int[]{u,child});
            }
        }
        return answer;
    }
    public static int longestChain(int n,int[][] nb) {
        int[] parent=new int[n],order=new int[n]; Arrays.fill(parent,-1);
        int size=1; order[0]=0; parent[0]=0;
        for(int i=0;i<size;i++) for(int u:nb[order[i]]) if(u!=parent[order[i]]) {
            parent[u]=order[i]; order[size++]=u;
        }
        int[] down=new int[n]; int best=0;
        for(int i=size-1;i>=0;i--) {
            int v=order[i],one=0,two=0;
            for(int u:nb[v]) if(parent[u]==v) {
                int len=down[u]+1;
                if(len>one) {two=one;one=len;} else if(len>two) two=len;
            }
            down[v]=one; best=Math.max(best,one+two);
        }
        return best;
    }
}
