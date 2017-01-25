import java.util.Scanner;

class scan_user_parameter{
  double para[] = new double[5];
  int sum = 0;
  String mikaku[] = {"甘み[0-100]","酸味[0-100]","塩味[0-100]","辛さ[0-100]","苦味[0-100]"};
    public scan_user_parameter(){
        for (int i = 0;i < para.length;i++ ) {
          System.out.print(mikaku[i]+":");
          Scanner scan = new Scanner(System.in);
          int n = Integer.parseInt(scan.next());
          if (n >= 0 && n <= 100) {
              para[i] = n;
          }else{
            while(true){
              if ( n >= 0 && n <= 100) {
                para[i] = n;
                break;
              }else{
                System.out.println("値は0~100の範囲で入力して下さい");
                System.out.print(mikaku[i]+":");
                scan = new Scanner(System.in);
                n = Integer.parseInt(scan.next());
              }
            }
          }
          //System.out.println(" :"+str);
        }
        
        //for(double data : para){
        	//sum += data;
        //}
        double tmp = para[0];
        for(int i = 0; i < para.length; i++){
        	para[i] /= tmp;
        }
    }

    //getter
    public double[] get_user_parameter(){
      return para;
    }
}
