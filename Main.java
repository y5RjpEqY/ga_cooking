//甘み、酸味、塩辛味、辛さ、苦味
//醤油、味噌、料理酒、みりん、お酢、キムチ、豆乳、カレールー、砂糖、塩、赤ワイン、白ワイン、故障,マヨネーズ、牛乳、ラム酒、コーヒー、タバスコ、レモン、グレープフルーツジュース
//全体の総量制限は要らない

import java.util.Random;
import java.util.*;

public class Main{
    static int N = 10; //遺伝子組の個数
    static int gSize = 18; //材料の個数
    static int ratioOfMutation = 1; //突然変異の確率
    static List<List<Integer>> ga = new ArrayList<List<Integer>>();
    static List<Material> material = new ArrayList<Material>();
    static Random rnd = new Random();
    static scan_user_parameter user_parameter;
    static double eval;
    static int no1, no2, wo1, wo2;
    static List<Double> evallist = new ArrayList<Double>();

    public static void main(String argc[]){
      int ran = rnd.nextInt(2);

      //userのパラメータ入力
      user_parameter = new scan_user_parameter();

      //材料の初期化
      set_material();

      //遺伝子たちを生成
      for(int i=0; i<N; i++){
        List<Integer> tmp = new ArrayList<Integer>();
        ga.add(tmp);
      }
      //遺伝子たちを0で初期化
      for(int i=0; i<N; i++){
        for(int j=0; j<gSize; j++){
          ga.get(i).add(0);
        }
      }
      //それぞれの遺伝子をランダムに作成する
      for(int i=0; i<N; i++){
        for(int j=0; j<gSize; j++){
          while(true){
            ran = rnd.nextInt(10);
            ga.get(i).set(j,ran);
            if(check_range(ga.get(i))){
              break;
            }
          }
        }
      }

      //GAアルゴリズム！！
      for(int i=0; i<N; i++){
        //評価値を初期化
        evallist.add(0.0);
      }

      for(int i=0; i<10; i++){
        //実際に評価
        evallist.set(i, calc(ga.get(i)));
      }

      //System.out.println(evallist);
      //交叉に使う上位2つの番号をno1,no2に格納
      no1 = get_no1(evallist);
      no2 = get_no2(evallist);

      //消える下位2つの番号をwo1,wo2に格納
      wo1 = get_wo1(evallist);
      wo2 = get_wo2(evallist);

      //System.out.println(no1+":"+no2+":"+wo1+":"+wo2);

      //遺伝子たちを表示
      System.out.println("#####初期値#####");
      //putGs();
      //それぞれの味パラメータを表示
      for(int i=0; i<N; i++){
        putAZI(ga.get(i));
      }

      //それぞれの味パラメータのズレを表示
      //要するに評価関数
      for(int i=0; i<N; i++){
        eval = calc(ga.get(i));
        System.out.println("目標とのズレ："+eval);
      }

      for(int z=0; z<10000; z++){
        //交叉
        changeGs();

        //突然変異
        for(int i=0; i<N; i++){
          //ran = rnd.nextInt(100);
          //if(ran < ratioOfMutation){
            //System.out.println("######"+i+"#####");
            mutation(ga.get(i));
           // check_range(ga.get(i));
          //}
        }

      //それぞれの味パラメータを表示
      for(int i=0; i<N; i++){
        //putAZI(ga.get(i));
      }

        //表示
       // putGs();
        for(int i=0; i<N; i++){
          eval = calc(ga.get(i));
          //System.out.println("目標とのズレ："+eval);
        }

        for(int i=0; i<10; i++){
          //実際に評価
          evallist.set(i, calc(ga.get(i)));
        }

        //交叉に使う上位2つの番号をno1,no2に格納
        no1 = get_no1(evallist);
        no2 = get_no2(evallist);

        //消える下位2つの番号をwo1,wo2に格納
        wo1 = get_wo1(evallist);
        wo2 = get_wo2(evallist);
      }

      ////最終結果
      System.out.println("#####最終結果#####");
      //それぞれの味パラメータを表示
      for(int i=0; i<N; i++){
        putAZI(ga.get(i));
      }
      System.out.println("==============");
      putAZI(ga.get(no1));
      System.out.println(ga.get(no1));
      System.out.println(evallist.get(no1));
    }

    static void set_material(){
      for(int i=0; i<gSize; i++){
        Material tmp = new Material();
        switch (i){
          //甘み、酸味、塩辛味、辛味、苦味
          case 0:
          //醤油
          tmp.set_all(0,1,1,0,0);
          break;
          case 1:
          //味噌
          tmp.set_all(1,0,1,0,1);
          break;
          case 2:
          //料理酒
          tmp.set_all(0,1,0,0,0);
          break;
          case 3:
          //みりん
          tmp.set_all(3,0,0,0,0);
          break;
          case 4:
          //お酢
          tmp.set_all(0,6,0,0,0);
          break;
          case 5:
          //キムチ
          tmp.set_all(0,3,0,5,0);
          break;
          case 6:
          //豆乳
          tmp.set_all(1,0,0,0,1);
          break;
          case 7:
          //カレールー
          tmp.set_all(0,1,2,2,0);
          break;
          case 8:
          //砂糖
          tmp.set_all(6,0,0,0,0);
          break;
          case 9:
          //塩
          tmp.set_all(0,0,6,0,0);
          break;
          case 10:
          //赤ワイン
          tmp.set_all(0,1,0,0,3);
          break;
          case 11:
          //白ワイン
          tmp.set_all(0,3,0,0,2);
          break;
          case 12:
          //胡椒
          tmp.set_all(0,0,3,6,0);
          break;
          case 13:
          //牛乳
          tmp.set_all(1,0,0,0,0);
          break;
          case 14:
          //マヨネーズ
          tmp.set_all(1,2,2,0,0);
          break;
          case 15:
          //レモン
          tmp.set_all(1,6,0,0,1);
          break;
          case 16:
          //グレープフルーツジュース
          tmp.set_all(3,3,0,0,4);
          break;
          case 17:
          //コーヒー
          tmp.set_all(0,1,0,0,6);
          break;
          case 18:
          //タバスコ
          tmp.set_all(0,1,1,9,0);
          break;
	  default :
	  tmp.set_all(0,0,0,0,0);
        }
        material.add(tmp);
      }
    }

    static double calc(List<Integer> x){
      double[] gas = new double[5];
      double eval = 0;
      double sum = 0;
      double u_para[];
      double users, gastmp;

      gas[0] = 0;
      gas[1] = 0;
      gas[2] = 0;
      gas[3] = 0;
      gas[4] = 0;

      for(int i=0; i<gSize; i++){
        gas[0] += x.get(i)*material.get(i).get_sweet();
        gas[1] += x.get(i)*material.get(i).get_acidity();
        gas[2] += x.get(i)*material.get(i).get_salty();
        gas[3] += x.get(i)*material.get(i).get_spicy();
        gas[4] += x.get(i)*material.get(i).get_bitter();
      }
      
      //sum = sweet + acidity + salty + spicy + bitter;
      gastmp = gas[0];
      gas[0] /= gastmp;
      gas[1] /= gastmp;
      gas[2] /= gastmp;
      gas[3] /= gastmp;
      gas[4] /= gastmp;
      

      //本当は指定した値との差異を計算し、返す
      u_para = user_parameter.get_user_parameter();
      for(int i=0; i<5; i++){
    	  gastmp = gas[i] - gas[0];
    	  users = u_para[i] - u_para[0];
    	  eval += Math.abs(gastmp - users);
      }
      
      /*
      eval += Math.abs(sweet - u_para[0]);
      eval += Math.abs(acidity - u_para[1]);
      eval += Math.abs(salty - u_para[2]);
      eval += Math.abs(spicy - u_para[3]);
      eval += Math.abs(bitter - u_para[4]);
      */

      return eval;
    }

    //評価値が1番悪い遺伝子組を取得
    static int get_wo1(List<Double> x){
      double max=0;
      int num=0;
      for(int i=0; i<N; i++){
        if(max < x.get(i)){
          max = x.get(i);
          num = i;
        }
      }

      return num;
    }

    //評価値が2番めに悪い遺伝子組を取得
    static int get_wo2(List<Double> x){
      double max1, max2;
      int num1, num2;

      max1 = max2 = 0;
      num1 = num2 = 0;
      for(int i=0; i<N; i++){
        if(max1 < x.get(i)){
          max2 = max1;
          num2 = num1;
          max1 = x.get(i);
          num1 = i;
        }else if(max2 < x.get(i)){
          max2 = x.get(i);
          num2 = i;
        }
      }

      return num2;
    }

    //評価値が一番いい遺伝子組を取得
    static int get_no1(List<Double> x){
      double min=99999999;
      int num=0;
      for(int i=0; i<N; i++){
        if(min > x.get(i)){
          min = x.get(i);
          num = i;
        }
      }

      return num;
    }

    //評価値が2番めにいい遺伝子組を取得
    static int get_no2(List<Double> x){
      double min1, min2;
      int num1, num2;

      min1 = 999999;
      min2 = 999999;
      num1 = num2 = 0;

      for(int i=0; i<N; i++){
        if(min1 > x.get(i)){
          min2 = min1;
          num2 = num1;
          min1 = x.get(i);
          num1 = i;
        }else if(min2 > x.get(i)){
          min2 = x.get(i);
          num2 = i;
        }
      }

      return num2;
    }

    //表示
    static void putGs(){
      for(int i=0; i<N; i++){
        System.out.println(ga.get(i));
      }
    }

    static void putAZI(List<Integer> x){
      int sweet, acidity, salty, spicy, bitter;

      sweet = 0;
      acidity = 0;
      salty = 0;
      spicy = 0;
      bitter = 0;

      for(int i=0; i<gSize; i++){
        sweet += x.get(i)*material.get(i).get_sweet();
        acidity += x.get(i)*material.get(i).get_acidity();
        salty += x.get(i)*material.get(i).get_salty();
        spicy += x.get(i)*material.get(i).get_spicy();
        bitter += x.get(i)*material.get(i).get_bitter();
      }

      System.out.println("甘さ:"+sweet+" 酸味:"+acidity+" 塩辛さ:"+salty+" 辛さ:"+spicy+" 苦味:"+bitter);
    }

    //交叉
    static void changeGs(){
      List<Integer> temp = new ArrayList<Integer>();
      List<Integer> top_1 = ga.get(no1);
      List<Integer> top_2 = ga.get(no2);
      int i = 0;

      while (i < gSize) {
        if (i < gSize/2) {
          temp.add(top_1.get(i));
        }else{
          temp.add(top_2.get(i));
        }
        i++;
      }
      ga.set(wo1,temp);

      i = 0;
      while (i < gSize) {
        if (i < gSize/2) {
          temp.add(top_2.get(i));
        }else{
          temp.add(top_1.get(i));
        }
        i++;
      }
      ga.set(wo2,temp);

    }
    //遺伝子が範囲内か確かめる
    static boolean check_range(List<Integer> x){
        int sweet, acidity, salty, spicy, bitter;
        double[] u_para;
      sweet = 0;
      acidity = 0;
      salty = 0;
      spicy = 0;
      bitter = 0;


      for(int i=0; i<gSize; i++){
        sweet += x.get(i)*material.get(i).get_sweet();
        acidity += x.get(i)*material.get(i).get_acidity();
        salty += x.get(i)*material.get(i).get_salty();
        spicy += x.get(i)*material.get(i).get_spicy();
        bitter += x.get(i)*material.get(i).get_bitter();
      }
      if(sweet  > 100 || acidity  > 100 || salty  > 100 || spicy  > 100 || bitter > 100){
        return false;
      }
      return true;
}
    //突然変異
    static void mutation(List<Integer> ga){
      for(int i=0; i<gSize; i++){
        int ran = rnd.nextInt(100);
        if(ran < ratioOfMutation){
          //while(true){
            int value = rnd.nextInt(100);
            ga.set(i,value);
            if(check_range(ga)){
              System.out.println("check_range is true");
            //  break;
            }
          //}
          //System.out.println("set" + value + " to " + i);
        }
      }
    }
}
