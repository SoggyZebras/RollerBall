package edu.colostate.cs.cs414.soggyZebras.rollerball.Client.menu;

public class MenuDemoRunner implements Runnable{

		public MenuDemoRunner(){}

		public void run(){
        new MenuGUI();
    }

    public static void main(String []args){
		    MenuDemoRunner r = new MenuDemoRunner();
            r.run();
    }
}
