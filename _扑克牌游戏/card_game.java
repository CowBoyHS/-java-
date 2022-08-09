import java.util.*;

//扑克牌类
class Card{
  public int count = 0;//用来计被匹配了多少次，1代表1次，以此类推
  //类体中只能对变量进行定义，构造函数中才能具体赋值
  //牌的花色用枚举去存储
  public enum Suit{
    diamonds,clubs,hearts,spades
  }
  //牌的点数用枚举去存储
  public enum Face{
    Ace,Deuce,Three,Four,Five,Six,Seven,Eight,Nine,Ten,Jack,Queen,King
  }
  private Suit suit;
  private Face face;
  public Card(Face f,Suit s){
    this.suit = s;
    this.face = f;
  }
  //提供一个suit接口
  public Suit getSuit(){
    return suit;
  }
  //提供一个face接口
  public Face getFace(){
    return face;
  }
  //使得输出类时输出我们想要的结果
  public String toString(){
    return String.format("%s of %s",face,suit);
  }
}

public class card_game {
  private List<Card> list;
  public card_game(){
    Card[] deck = new Card[52];
    int count = 0;
    //增强型写法
    for(Card.Suit suit : Card.Suit.values()){
      for(Card.Face face : Card.Face.values()){
        //初始化对象
        deck[count] = new Card(face,suit);
//        System.out.println(deck[count]);
        ++count;
      }
    }
    //将数组转成集合，使用java自带的shuffle方法去打乱一副扑克牌
    list = Arrays.asList(deck);//静态的,不能删除和增加元素
    Collections.shuffle(list);
  }
  //打印一副扑克牌
  public void printCards(){
    for(int i = 0;i < list.size();i++){
      System.out.printf("%-20s%s",list.get(i),((i+1)%4==0)? "\n" : "");
    }
  }

  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    card_game myGame = new card_game();

    //保存游戏进行中的牌
    List<Card> gaming_deck = new ArrayList<>();

    int i = 0;
    //表示是否发完牌
    while(i < 52){
      //发牌，并将牌放在另一个牌堆中的顶部
      System.out.println("发的牌为：" + myGame.list.get(i));

      gaming_deck.add(myGame.list.get(i));
      //判断是否有重复的牌，有的话控制台输出，让用户选择是否需要收牌,
      //如果要收牌，则将牌顶到最底层的且点数一样的牌之间的牌全部收走并放在另一个牌堆中，并继续发牌
      if(i>=1){
        for(int j = 0;j < gaming_deck.size()-1;++j){
          if(myGame.list.get(i).getFace() == gaming_deck.get(gaming_deck.size()-j-2).getFace()){
            if(gaming_deck.get(gaming_deck.size()-j-2).count == 0){
              myGame.list.get(i).count = 1;
              gaming_deck.get(gaming_deck.size()-j-2).count =1;
              System.out.println(myGame.list.get(i) + "与" + gaming_deck.get(gaming_deck.size()-j-2) + "的点数第一次一样，是否要收牌?");
              System.out.println("1、是 2、否");
              //接收用户输入
              int result = s.nextInt();
              //判断输入是否合理
              while(result !=1 & result != 2){
                System.out.println("输入有误，请重新输入！");
                result = s.nextInt();
              }
              //表示要收牌
              if(result == 1){
                //执行收牌操作
                gaming_deck.subList(gaming_deck.size()-j-2, gaming_deck.size()).clear();//可能会越界
              }else{
                break;//继续发牌
              }
              //表示有匹配过，但未收
            }else if(gaming_deck.get(gaming_deck.size()-j-2).count == 1){
              myGame.list.get(i).count = 2;
              gaming_deck.get(gaming_deck.size()-j-2).count =2;
              //将最后一张的count也改变
              for(int k = gaming_deck.size()-j-2 -1;k > 0;k--){
                if(myGame.list.get(i).getFace() == gaming_deck.get(k).getFace()){
                  gaming_deck.get(k).count = 2;
                }
              }
              System.out.println(myGame.list.get(i) + "与" + gaming_deck.get(gaming_deck.size()-j-2) + "的点数第二次一样，是否要收牌?");
              System.out.println("1、是 2、否");
              //接收用户输入
              int result = s.nextInt();
              //判断输入是否合理
              while(result !=1 & result != 2){
                System.out.println("输入有误，请重新输入！");
                result = s.nextInt();
              }
              //表示要收牌
              if(result == 1){
                //执行收牌操作,从牌尾开始遍历，找到最下面的那张相同点数的牌
                for(int k = 0 ;k < gaming_deck.size();k++){
                  if(gaming_deck.get(k).getFace() == myGame.list.get(i).getFace()){
                    gaming_deck.subList(k, gaming_deck.size()).clear();//将头与比较靠后的相同点数的牌之间的所有牌都收走
                    break;
                  }
                }
              }else{
                break;//继续发牌
              }
            }else if(gaming_deck.get(gaming_deck.size()-j-2).count == 2){

              myGame.list.get(i).count = 3;
              gaming_deck.get(gaming_deck.size()-j-2).count =3;
              //将最后一张的count也改变
              for(int k = gaming_deck.size()-j-2 -1;k > 0;k--){
                if(myGame.list.get(i).getFace() == gaming_deck.get(k).getFace()){
                  gaming_deck.get(k).count = 3;
                }
              }

              System.out.println(myGame.list.get(i) + "与" + gaming_deck.get(gaming_deck.size()-j-2) + "的点数第三次一样，是否要收牌?");
              System.out.println("1、是 2、否");
              //接收用户输入
              int result = s.nextInt();
              //判断输入是否合理
              while(result !=1 & result != 2){
                System.out.println("输入有误，请重新输入！");
                result = s.nextInt();
              }
              //表示要收牌
              if(result == 1){
                //执行收牌操作,从牌尾开始遍历，找到最下面的那张相同点数的牌
                for(int k = 0 ;k < gaming_deck.size();k++){
                  if(gaming_deck.get(k).getFace() == myGame.list.get(i).getFace()){
                    gaming_deck.subList(k, gaming_deck.size()).clear();//将头与比较靠后的相同点数的牌之间的所有牌都收走
                    break;
                  }
                }
              }else{
                break;//继续发牌
              }
            }else if(gaming_deck.get(gaming_deck.size()-j-2).count == 3){

              System.out.println(myGame.list.get(i) + "与" + gaming_deck.get(gaming_deck.size()-j-2) + "的点数第四次一样，是否要收牌?");
              System.out.println("1、是 2、否");
              //接收用户输入
              int result = s.nextInt();
              //判断输入是否合理
              while(result !=1 & result != 2){
                System.out.println("输入有误，请重新输入！");
                result = s.nextInt();
              }
              //表示要收牌
              if(result == 1){
                //执行收牌操作,从牌尾开始遍历，找到最下面的那张相同点数的牌
                for(int k = 0 ;k < gaming_deck.size();k++){
                  if(gaming_deck.get(k).getFace() == myGame.list.get(i).getFace()){
                    gaming_deck.subList(k, gaming_deck.size()).clear();//将头与比较靠后的相同点数的牌之间的所有牌都收走
                    break;
                  }
                }
              }else{
                break;//继续发牌
              }
            }
          }
        }
      }
      i++;
    }
    //判断是否牌堆中有余牌，有的话则控制台输出玩家输，否则输出玩家获胜
    if(gaming_deck.size() == 0){
      System.out.println("恭喜获胜！");
    }else{
      System.out.println("输了，继续努力！");
      System.out.println("余牌为:");
        for(int l = 0;l < gaming_deck.size();l++){
          System.out.printf("%-20s%s",gaming_deck.get(l),((l+1)%4==0)? "\n" : "");
        }
    }
  }
}
