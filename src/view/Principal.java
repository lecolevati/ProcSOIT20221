package view;

import controller.ProcessosController;

public class Principal {

	public static void main(String[] args) {
		ProcessosController pCont = new ProcessosController();
//		pCont.os();
		
//		String process = "regedit.exe";
//		pCont.initProcess(process);

//		String process = "tracert www.fateczl.edu.br";
//		pCont.readTraceRoute(process);

		String opc = "notepad.exe";
		pCont.killProcess(opc);
		
	}

}
