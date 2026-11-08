import java.util.*;
public class Marker {
    public static int[] prefixFunction(String p) {
        int[] pi=new int[p.length()];
        for(int i=1;i<p.length();i++) {
            int j=pi[i-1];
            while(j>0 && p.charAt(i)!=p.charAt(j)) j=pi[j-1];
            if(p.charAt(i)==p.charAt(j)) j++;
            pi[i]=j;
        }
        return pi;
    }
    public static int[] findAll(String text,String p) {
        if(p.isEmpty()) return new int[0];
        int[] pi=prefixFunction(p); ArrayList<Integer> hits=new ArrayList<>(); int j=0;
        for(int i=0;i<text.length();i++) {
            while(j>0 && text.charAt(i)!=p.charAt(j)) j=pi[j-1];
            if(text.charAt(i)==p.charAt(j)) j++;
            if(j==p.length()) { hits.add(i-p.length()+1); j=pi[j-1]; }
        }
        int[] result=new int[hits.size()];
        for(int i=0;i<result.length;i++) result[i]=hits.get(i);
        return result;
    }
    private static int code(char c) { return c=='A'?1:c=='C'?2:c=='G'?3:4; }
    public static int sharedWords(String a,String b,int k) {
        if(k<=0 || a.length()<k || b.length()<k) return 0;
        final long mod=1_000_000_007L, base=5L;
        long power=1;
        for(int i=1;i<k;i++) power=power*base%mod;
        Map<Long,List<Integer>> positions=new HashMap<>();
        long h=0;
        for(int i=0;i<k;i++) h=(h*base+code(b.charAt(i)))%mod;
        positions.computeIfAbsent(h,x->new ArrayList<>()).add(0);
        for(int s=1;s+k<=b.length();s++) {
            h=(h-code(b.charAt(s-1))*power%mod+mod)%mod;
            h=(h*base+code(b.charAt(s+k-1)))%mod;
            positions.computeIfAbsent(h,x->new ArrayList<>()).add(s);
        }
        h=0; for(int i=0;i<k;i++) h=(h*base+code(a.charAt(i)))%mod;
        int count=0;
        for(int s=0;s+k<=a.length();s++) {
            List<Integer> candidates=positions.get(h); boolean found=false;
            if(candidates!=null) for(int pos:candidates) {
                int j=0; while(j<k && a.charAt(s+j)==b.charAt(pos+j)) j++;
                if(j==k) { found=true; break; }
            }
            if(found) count++;
            if(s+k<a.length()) {
                h=(h-code(a.charAt(s))*power%mod+mod)%mod;
                h=(h*base+code(a.charAt(s+k)))%mod;
            }
        }
        return count;
    }
}
