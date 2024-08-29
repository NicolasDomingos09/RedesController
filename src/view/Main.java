package view;

import java.util.Scanner;

import controller.RedesController;

public class Main {

	public static void main(String[] args) {
		RedesController rCont = new RedesController();
		Scanner scan = new Scanner(System.in);
		
		String menu = """
				1 - Mostrar IPv4
				2 - Teste de ping
				9 - Finalizar
				""";
		
		int opc = 0;
		while(opc != 9) {
			System.out.println(menu);
			opc = scan.nextInt();
			
			switch (opc) {
			
			case 1:
				rCont.callIpConfig();
				break;
			case 2:
				rCont.testPing();
				break;
			case 9:
				System.out.println("Finalizando");
				break;
			default:
				System.out.println("Opção inválida");
			}
		}
		scan.close();
	}

}
