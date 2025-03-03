package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwInit;

public class Window {
    //さらに、シングルトン化を行う
    //理由は、初期化時に読み込まれることを防ぎ、かつgetされたときのみ読み込まれるようにするためにここに内部静的クラスを作成し、
    //さらに、パブリックのfinal staticで変数 Instance = new 外部クラス();と置くことで、このInstance変数が参照されたときのみ
    //読み込まれる。

    private int width,height;
    private String title;

    private Window(){
        width = 1920;
        height = 1080;
        title = "Mario";
    }

    public static Window get(){
        return WindowHolder.INSTANCE;
    }

    public void run(){
        //とりあえず、これでversionの初期化。
        //initとloopメソッドの作成。
        System.out.println("This is LWJGL:" + Version.getVersion());

        init();
        loop();
    }

    public void init(){
        //まず、エラーのコールバック先を作る。
        //コールバックとは、lwjglでなにかエラーが起こったときにここにエラーが集まる。
        GLFWErrorCallback.createPrint(System.err).set();
        //これで、エラーが起こったときSystemのerrを通じてエラーを出すよという宣言。
        if(!glfwInit()){
            throw new IllegalStateException("init failed");
        }

        glfwDefaultWindowHints();

    }

    public void loop(){

    }

    public static class WindowHolder{
        //そもそも、この内部クラスのスコープを理解していない。
        //まあ少なくともパブリックかつプライベートコンストラクタだから、多分大丈夫。
        //しかも、これ以外にもシングルトンの作り方があるみたい。
        //内部クラスを作らない作り方だと
        //private staticでWindow変数をつくってnullで初期化、get()で
        //その変数がnullのときだけインスタンスを作って、あとは変数をリターンする。
        //こっちのほうが理解はしやすい。
        //こっちが複雑なのは、なれない内部クラスを使っているから。
        //でもifとか使わなくてもいいからget()の可読性は上がってるかも。
        private static final Window INSTANCE = new Window();
    }
}
