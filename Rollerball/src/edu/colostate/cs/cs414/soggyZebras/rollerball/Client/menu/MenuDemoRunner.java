package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

public class MenuDemoRunner implements Runnable{

    String ip;
		public MenuDemoRunner(String ip){this.ip = ip;}

		public void run(){
        new MenuGUI(ip);
    }

    public static void main(String []args){
		    MenuDemoRunner r = new MenuDemoRunner(args[0]);
            r.run();
    }
}
