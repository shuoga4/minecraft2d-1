import jade.Window;

public class HelloWorld {
    public static void main(String[] args) {
        //まずはwindowクラスを作ってmainからwindow構造を切り離す。
        //基本内部構造と外の出力をクラスで分けるのは、多分オブジェクト指向の仕様。
        //jadeという名前は、エンジンの名前。
        Window window = Window.get();
        window.run();
        //これから、ファクトリーメソッドでインスタンスの作成及び、runメソッドでwindowを作る。
        //ここで、ファクトリーメソッドなのは、windowが複数作られるのを防ぐため。
        //
    }
}