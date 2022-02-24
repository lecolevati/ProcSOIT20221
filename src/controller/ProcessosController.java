package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessosController {

	public ProcessosController() {
		super();
	}

	public void os() {
		String os = System.getProperty("os.name");
		System.out.println(os);

		String ver = System.getProperty("os.version");
		System.out.println(ver);

		String arch = System.getProperty("os.arch");
		System.out.println(arch);
	}

	public void initProcess(String process) {
		try {
			Runtime.getRuntime().exec(process);
		} catch (Exception e) {
			String erro = e.getMessage();
			if (erro.contains("2")) {
				System.err.println("Aplicação não encontrada");
			}
			if (erro.contains("740")) {
				StringBuffer buffer = new StringBuffer();
				buffer.append("cmd /c");
				buffer.append(" ");
				buffer.append(process);

				String cmdCred = buffer.toString();

				try {
					Runtime.getRuntime().exec(cmdCred);
				} catch (Exception e1) {
					System.err.println("Chamada de aplicação inválida");
				}
			}
		}
	}

	public void readProcess(String process) {
		try {
			Process p = Runtime.getRuntime().exec(process);
			InputStream leFluxo = p.getInputStream();
			InputStreamReader converteString = new InputStreamReader(leFluxo);
			BufferedReader buffer = new BufferedReader(converteString);
			String linha = buffer.readLine();
			while (linha != null) {
				System.out.println(linha);
				linha = buffer.readLine();
			}
			buffer.close();
			converteString.close();
			leFluxo.close();

		} catch (Exception e) {
			System.err.println("Chamada inválida");
		}
	}
	
	public void readTraceRoute(String process) {
		try {
			Process p = Runtime.getRuntime().exec(process);
			InputStream fluxo = p.getInputStream();
			InputStreamReader convString = new InputStreamReader(fluxo);
			BufferedReader buffer = new BufferedReader(convString);
			String linha = buffer.readLine();
			linha = buffer.readLine();
			linha = buffer.readLine();
			linha = buffer.readLine();
			linha = buffer.readLine();
			while (linha != null) {
				String[] vetLinha = linha.split("ms");
				int tamanho = vetLinha.length;
				String ip = vetLinha[tamanho - 1];
				
				if (ip.contains("[")) {
					String[] vetIp = ip.split("\\[");
					String newIp = vetIp[1].replace("]", "");
					System.out.println(newIp);
				} else {
					if (!ip.contains("tempo limite")) {
						System.out.println(ip.trim());
					}
				}
				
				linha = buffer.readLine();
				
			}
		} catch (IOException e) {
			System.err.println("Chamada inválida");
		}
	}
	
	
	public void killProcess(String opc) {
		String os = System.getProperty("os.name");
		String cmdNome = "";
		String cmdPid = "";
		if (os.contains("Windows")) {
			cmdNome = "TASKKILL /IM";
			cmdPid = "TASKKILL /PID";
		}
		if (os.contains("Linux")) {
			cmdPid = "kill -9";
			cmdNome = "pkill -f";
		}

		int pid = 0;
		StringBuffer buffer = new StringBuffer();
		
		try {
			pid = Integer.parseInt(opc);
			buffer.append(cmdPid);
			buffer.append(" ");
			buffer.append(pid);
		} catch (NumberFormatException e) {
			buffer.append(cmdNome);
			buffer.append(" ");
			buffer.append(opc);
		}
		initProcess(buffer.toString());
	}
	
	
}
