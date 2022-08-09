import java.util.Random;
import java.util.Scanner;
class player{
  public int m_id;
  public int[] award = new int[7];//获奖情况 其中数组1对应的位置代表状元 2代表对堂 3代表三红 4代表四进 5代表二举 6代表一秀
  public int player_flag = 0;//如果有获得状元，则用来比较状元之间追饼关系的flag
  player(int id){
    //初始化学生id和获奖情况
    this.m_id = id;
    for(int i =1; i<=6;i++){
      this.award[i] = 0;
    }
  }
}
public class bobing{
  static int prize_flag = 0;//用来标记状元的等级 4个4 (1) < 五子登科(2) < 状元插金花(3)
  public static void main(String[] args) {
    System.out.print("请输入玩家个数：");
    //1.读入玩家数据
    java.util.Scanner s = new java.util.Scanner(System.in);
    int player_nums = s.nextInt();
    //判断输入数据是否合法，不合法则重新输入
    while(player_nums<=1){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("请输入玩家个数：");
      player_nums = s.nextInt();
    }
//      System.out.println(player_nums);
    //2.设置奖品数量
    int zhuangyuan = 0;
    int duitang = 0;
    int sanhong = 0;
    int sijing = 0;
    int erju = 0;
    int yixiu = 0;
    //设置状元奖品数只能为1个或者没有
    System.out.print("状元奖品数为：");
    java.util.Scanner s1 = new java.util.Scanner(System.in);
    zhuangyuan = s1.nextInt();
    //用一个常量保存数据
    final int ZHUANGYUAN = zhuangyuan;
    while(zhuangyuan<0 ||zhuangyuan >1){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("状元奖品数为：");
      zhuangyuan = s1.nextInt();
    }
    //设置对堂奖品数
    System.out.print("对堂奖品数为：");
    java.util.Scanner s2 = new java.util.Scanner(System.in);
    duitang = s2.nextInt();
    final int DUITANG = duitang;
    while(duitang<0){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("对堂奖品数为：");
      duitang = s2.nextInt();
    }
    //设置三红奖品数
    System.out.print("三红奖品数为：");
    java.util.Scanner s3 = new java.util.Scanner(System.in);
    sanhong = s3.nextInt();
    final int SANHONG = sanhong;
    while(sanhong<0){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("三红奖品数为：");
      sanhong = s3.nextInt();
    }
    //设置四进奖品数
    System.out.print("四进奖品数为：");
    java.util.Scanner s4 = new java.util.Scanner(System.in);
    sijing = s4.nextInt();
    final int SIJING = sijing;
    while(sijing<0){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("四进奖品数为：");
      sijing = s4.nextInt();
    }
    //设置二举奖品数
    System.out.print("二举奖品数为：");
    java.util.Scanner s5 = new java.util.Scanner(System.in);
    erju = s5.nextInt();
    final int ERJU = erju;
    while(erju<0){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("二举奖品数为：");
      erju = s5.nextInt();
    }
    //设置一秀奖品数
    System.out.print("一秀奖品数为：");
    java.util.Scanner s6 = new java.util.Scanner(System.in);
    yixiu = s6.nextInt();
    final int YIXIU = yixiu;
    while(yixiu<0){
      System.out.println("您输入有误，请重新输入！");
      System.out.print("一秀奖品数为：");
      yixiu = s6.nextInt();
    }
    //所有奖品都未被赢走的表示方法:zhuangyuan || duitang || sanhong || sijing || erju || yixiu
    player[] players = new player[player_nums+1];//创建对象数组,构造每个学生的属性
    for(int i = 1;i <= player_nums;i++){
      //给每位学生赋值上相应的id
      players[i] = new player(i);
    }
    int player_id =1;
    //循环玩家掷筛子,第一层循环
    while(zhuangyuan>0 | duitang>0 | sanhong>0 | sijing>0 | erju>0 | yixiu>0){
      System.out.print( player_id + "号玩家请掷筛子: ");
      //显示六个筛子的点数
      int[] player_rands = new int[6];
      //第二层循环
      for(int i = 0;i < 6;++i){
        //生成随机数，模拟色子
        Random ran = new Random();
        int rand = ran.nextInt(6)+1;
        //存储每个筛子的点数
        player_rands[i] = rand;
        if(i==5){
          //去掉显示筛子内容中的最后一个逗号
          System.out.print(rand);
          //1.进行每个人的判断
          int one_flag = 0;
          int two_flag = 0;
          int three_flag = 0;
          int four_flag = 0;
          int five_flag = 0;
          int six_flag = 0;
          //第三层循环，统计筛子的结果
          for(int l = 0; l < 6;++l){
            if(player_rands[l] == 1){
              one_flag++;
            }
            if(player_rands[l] == 2){
              two_flag++;
            }
            if(player_rands[l] == 3){
              three_flag++;
            }
            if(player_rands[l] == 4){
              four_flag++;
            }
            if(player_rands[l] == 5){
              five_flag++;
            }
            if(player_rands[l] == 6){
              six_flag++;
            }
          }
//          System.out.println("  " + four_flag);
          //第三层循环结束
          //6个4，六杯红,玩家获得全部奖品
          if(four_flag == 6){
            players[player_id].award[1] = YIXIU;
            players[player_id].award[2] = DUITANG;
            players[player_id].award[3] = SANHONG;
            players[player_id].award[4] = SIJING;
            players[player_id].award[5] = ERJU;
            players[player_id].award[6] = YIXIU;
            yixiu = 0;
            erju = 0;
            sanhong = 0;
            sijing = 0;
            duitang = 0;
            zhuangyuan = 0;
            System.out.println(" 六杯红，恭喜！！！");
            //设置其他玩家的获奖情况
            for(int j = 1;j <= player_nums;++j){
              for(int k = 1 ;k <= 6;++k){
                if(j == player_id){
                  break;
                }else{
                  players[j].award[k] = 0;
                }
              }
            }
          }
          //3个4
          if(four_flag == 3){
            if(sanhong>0){
                players[player_id].award[3]++;
                sanhong--;
                System.out.println(" 三红，恭喜");
            }else{
              System.out.println(" 无奖，继续努力");
            }
          }
          //2个4的情况
          if(four_flag == 2) {
            //四进的情况
            if(one_flag == 4 | two_flag == 4 | three_flag == 4 | five_flag == 4 | six_flag == 4) {
              if (sijing > 0) {
                players[player_id].award[4]++;
                sijing--;
                System.out.println(" 四进，恭喜");
              } else {
                //二举
                if (erju > 0) {
                  players[player_id].award[5]++;
                  erju--;
                  System.out.println(" 二举，恭喜");
                } else {
                  System.out.println(" 无奖，继续努力");
                }
              }
            }else{
              //二举
              if (erju > 0) {
                players[player_id].award[5]++;
                erju--;
                System.out.println(" 二举，恭喜");
              } else {
                System.out.println(" 无奖，继续努力");
              }
            }
          }
          //1个4的情况
          if(four_flag == 1){
            //五子登科的情况
            if(one_flag == 5 | two_flag == 5| three_flag == 5| five_flag == 5| six_flag == 5){
              if(prize_flag < 2){
                players[player_id].player_flag = 2;
                prize_flag = 2;
                System.out.println(" 状元，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }
            //对堂的情况
            else if(one_flag == 1 & two_flag == 1 & three_flag == 1 & five_flag == 1 & six_flag == 1){
              if(duitang > 0){
                players[player_id].award[2]++;
                duitang--;
                System.out.println(" 对堂，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }
            //四进
            else if(one_flag == 4 | two_flag == 4 | three_flag == 4 | five_flag == 4 | six_flag == 4){
              if (sijing > 0) {
                players[player_id].award[4]++;
                sijing--;
                System.out.println(" 四进，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }
            //一秀的情况
            else {
              if(yixiu > 0){
                players[player_id].award[6]++;
                yixiu--;
                System.out.println(" 一秀，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }
          }
          //4个4
          if(four_flag == 4){
            //判断是否为状元插金花
            if(one_flag == 2){
              if(prize_flag < 3){
                prize_flag = 3;
                players[player_id].player_flag = 3;
                System.out.println(" 状元，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }else{
              //判断是否为状元
              if(prize_flag < 1){
                prize_flag = 1;
                players[player_id].player_flag = 1;
                System.out.println(" 状元，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }
          }
          //5个4
          if(four_flag == 5){
            if(prize_flag <2){
              prize_flag = 2;
              players[player_id].player_flag = 2;
              System.out.println(" 状元，恭喜");
            }else{
              System.out.println(" 无奖，继续努力");
            }
          }
          //0个4
          if(four_flag == 0){
            //五子登科的情况
            if(one_flag == 5 | two_flag == 5| three_flag == 5| five_flag == 5| six_flag == 5){
              if(prize_flag < 2){
                players[player_id].player_flag = 2;
                prize_flag = 2;
                System.out.println(" 状元，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }
            //四进的情况
            else if(one_flag == 4 | two_flag == 4 | three_flag == 4 | five_flag == 4 | six_flag == 4){
              if (sijing > 0) {
                players[player_id].award[4]++;
                sijing--;
                System.out.println(" 四进，恭喜");
              }else{
                System.out.println(" 无奖，继续努力");
              }
            }else{
              System.out.println(" 无奖，继续努力");
            }
          }
          //2.将player_rands的数组内容重新初始化
          for(int k = 0 ;k < 6;++k){
            player_rands[k] = 0;
          }
          break;//退出第二层循环
        }
        System.out.print(rand + ",");
      }
      System.out.println();
      //到下一名玩家
      ++player_id;
      if(player_id == player_nums+1){
        player_id = 1;
      }
      //结束条件
      if(duitang==0 & sanhong==0 & sijing==0 & erju==0 & yixiu==0){
        //退出第一层循环
        //此时如果已经有状元被决出则可以结束该游戏，否则继续游戏决出第一个获得状元的玩家结束游戏。可以避免状元奖品没人拿的情况出现
        if(prize_flag>0){
          break;
        }
      }
    }
    //3.实现状元的归属
    for(int i = 1;i <= player_nums;++i){
      if( players[i].player_flag == prize_flag){
        players[i].award[1]++;
        zhuangyuan--;
        break;
      }
    }
    //4.输出获奖情况
    for(int i = 1;i <= player_nums;++i){
      System.out.print( i + "号玩家获奖情况:");
      for(int j = 1;j <= 6;++j){
        if( j == 1){
          System.out.print("状元:" + players[i].award[j] + " ");
        }
        if( j == 2){
          System.out.print("对堂:" + players[i].award[j] + " ");
        }
        if(j == 3){
          System.out.print("三红:" + players[i].award[j] + " ");
        }
        if(j == 4){
          System.out.print("四进:" + players[i].award[j] + " ");
        }
        if(j == 5){
          System.out.print("二举:" + players[i].award[j] + " ");
        }
        if(j == 6){
          System.out.print("一秀:" + players[i].award[j] + " ");
        }
      }
      System.out.println();
    }
  }
}