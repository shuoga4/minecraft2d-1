package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    //さらに、シングルトン化を行う
    //理由は、初期化時に読み込まれることを防ぎ、かつgetされたときのみ読み込まれるようにするためにここに内部静的クラスを作成し、
    //さらに、パブリックのfinal staticで変数 Instance = new 外部クラス();と置くことで、このInstance変数が参照されたときのみ
    //読み込まれる。

    private int width,height;
    private String title;
    private long glfwWindow;

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
            throw new IllegalStateException("GLFW initialization failed");
        }

        glfwDefaultWindowHints();
        // これは、glfw側が勝手に決めたデフォルト設定詰め込みパック
        // おそらく、一つ一つ設定することも可能だろうが、面倒くさいのでデフォ。
        glfwWindowHint(GLFW_VISIBLE,GLFW_FALSE);
        //見えない化
        glfwWindowHint(GLFW_MAXIMIZED,GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE,GLFW_TRUE);

        // ウィンドウ作成、glfwCreateWidowは、long値を返す。
        // これは、メモリースペースのどの位置にwindowを作るかを返す関数。
        glfwWindow = glfwCreateWindow(this.width,this.height,this.title,NULL,NULL);

        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // OpenGLのコンテキストカレントを作る。
        // 何を意味しているのかはわからない。
        glfwMakeContextCurrent(glfwWindow);
        //つまり、コンテキストカレントとは、OpenGLの描画先をWindowに指定する作業のことみたい
        //これは、下にあるGL.createCapabilities()の文と対応している。
        //一スレッド = 一ウィンドウ = 一 ContextCurrent + createCapabilities
        // で対応しているみたい　

        //垂直同期on
        glfwSwapInterval(1);

        // ウィンドウ可視化
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        // この行は、LWJGL と GLFW の OpenGL コンテキスト、または外部で管理されるコンテキストとの相互運用にとって重要です。
        // LWJGL は、現在のスレッドで現在のコンテキストを検出し、GLCapabilities インスタンスを作成して、
        // OpenGL バインディングを使用できるようにします。
        GL.createCapabilities();

        //ここまでで実行すると、一瞬ウィンドウが現れてすぐ消える。
    }

    public void loop(){
        //glfwWindowShouldCloseは、多分Windowが閉じる命令や処理を受けたときにやりたいこと先にやってから終了するための
        //boolean何だと思う。大体のwindowは閉じる前に処理走るよね
        while (!glfwWindowShouldClose(glfwWindow)) {
            // eventの取得
            glfwPollEvents();
            //次のチュートリアルで使うらしい。

            glClearColor(1.0f,0.0f,0.0f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);
            // これは、OpenGLに、どのようにバッファを消すかを指定できる。
            // これらは、赤のカラーバッファービットを使うという意味。
            //クリアカラーを指定して、それを画面にフラッシュする。

            glfwSwapBuffers(glfwWindow);
            //これが自動的にバッファをスワップするので心配は無い？らしい。
            // ダブルバッファといって、一度描画する前にバックバッファに描画してからフロントバッファに送るらしい。
        }
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
