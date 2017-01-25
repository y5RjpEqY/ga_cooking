
public class Material{
  protected int sweet;
  protected int acidity;
  protected int salty;
  protected int spicy;
  protected int bitter;

  public Material(){
    sweet = 0;
    acidity = 0;
    salty = 0;
    spicy = 0;
    bitter = 0;
  }

  //setter
  public void set_sweet(int s){
    sweet = s;
  }

  public void set_acidity(int a){
    acidity = a;
  }

  public void set_salty(int s){
    salty = s;
  }

  public void set_spicy(int s){
    spicy = s;
  }

  public void set_bitter(int b){
    bitter = b;
  }

  public void set_all(int a, int b, int c, int d, int e){
    set_sweet(a);
    set_acidity(b);
    set_salty(c);
    set_spicy(d);
    set_bitter(e);
  }

  //getter
  public int get_sweet(){
    return sweet;
  }

  public int get_acidity(){
    return acidity;
  }

  public int get_salty(){
    return salty;
  }

  public int get_spicy(){
    return spicy;
  }

  public int get_bitter(){
    return bitter;
  }
}
