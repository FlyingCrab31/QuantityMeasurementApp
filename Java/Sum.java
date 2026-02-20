class calc{
    protected int div(int a,int b){
        int res=a/b;
        return res;
    }
}
public class Sum {
    public static void main(String[] args) {
    calc obj=new calc();
    int x=obj.div(10, 2);
    System.out.println(x);

    }
    
}
