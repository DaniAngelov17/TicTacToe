package SubPanels;
public class Subset {
    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,7,9,11,13,15};
        int sum = 0;
            for (int i : nums) {
                for (int j : nums) {
                    for (int k : nums) {
                        sum = i+j+k;
                        if(sum==30){
                            System.out.println(i +" " + j +" "+k +"..."+sum);
                            break;
                        }
                        else{
                            System.out.println(i +" " + j +" "+k+"..."+sum);   
                        }
                    }
                    if(sum==30){
                        break;
                    }
                }
                if(sum==30){
                    break;
                }
            }
    }
}
