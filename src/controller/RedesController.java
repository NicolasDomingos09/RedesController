package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {
	
	public RedesController() {
		super();
	}
	
	private String identOS() {
		
		String sistema = System.getProperty("os.name");
		
		return sistema;
	}
	
	@SuppressWarnings ("deprecation")
	public void callIpConfig() {
		String OS = identOS();
		
		if(OS.contains("Windows")){
			try {
				Process proc = Runtime.getRuntime().exec("ipconfig");
				InputStream retorno = proc.getInputStream();
				InputStreamReader leitor = new InputStreamReader(retorno);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				String adapter = "";
				
				while(linha != null) {
					linha = buffer.readLine();
					
					if(linha == null) { //Bug fix: não sei pq o terminal bate num null, mas tá funcionando
						break;
					}
					if(linha.contains("Ethernet")) {
						adapter = linha;
					}
					if(linha.contains("IPv4")) {
						System.out.println("Adaptador: " + adapter);
						System.out.println(linha);
					}
				}
				
				retorno.close();
				leitor.close();
				buffer.close();
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		
		}else if (OS.contains("Linux")) {
			try {
				Process proc = Runtime.getRuntime().exec("ip addr");
				InputStream retorno = proc.getInputStream();
				InputStreamReader leitor = new InputStreamReader(retorno);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				
				while(linha != null) {
					linha = buffer.readLine();
					
					if(linha == null) { //Bug fix: Eu ainda não sei o porque ainda tem uma linha vazia e isso vai continuar aqui
						break;
					}
					
					if(linha.contains("inet")) {
						if(linha.contains("inet6")) {
							continue;
						}else {
							System.out.println(linha);
						}
					}
				}
				
				retorno.close();
				leitor.close();
				buffer.close();
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}

		}
	}
	

	@SuppressWarnings("deprecation")
	public void testPing() {
		String OS = identOS();
	
		if(OS.contains("Windows")) {
			try {
				Process proc = Runtime.getRuntime().exec("ping -4 -n 10 www.google.com.br");
				InputStream retorno = proc.getInputStream();
				InputStreamReader leitor = new InputStreamReader(retorno);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				
				while(linha != null) {
					linha = buffer.readLine();
					if(linha.contains("mili")) {
						linha = buffer.readLine();
						String[] stats = linha.split(",");
						String[] average = stats[(stats.length-1)].split("=");
						System.out.println("Average = " + average[(average.length-1)].trim()); //SIM 
					}
				}
				
				retorno.close();
				leitor.close();
				buffer.close();
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}else if (OS.contains("Linux")) {
			try {
				Process proc = Runtime.getRuntime().exec("ping -4 -c 10 www.google.com.br");
				InputStream retorno = proc.getInputStream();
				InputStreamReader leitor = new InputStreamReader(retorno);
				BufferedReader buffer = new BufferedReader(leitor);
				String linha = buffer.readLine();
				
				while(linha != null) {
					linha = buffer.readLine();
					if(linha.contains("rtt")) {
						String[] stats = linha.split("=");
						String[] values = stats[stats.length-1].split("/");
						System.out.println("Average = " + values[1]);
					}
				}
				
				retorno.close();
				leitor.close();
				buffer.close();
				
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}