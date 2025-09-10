package supermarketInventory;

import java.util.Scanner;

public class Inventory {
	public static void main(String[] args) {
		
		//Here defined the things
		final int MAX_PRODUCTS = 5;
		String[] namesProducts = new String[MAX_PRODUCTS];
		int[] quantityProducts = new int[MAX_PRODUCTS];
		int totalProducts = 0;
		Scanner input = new Scanner(System.in);
		
		
		System.out.println("------Registro De Productos------");
		
		for( int i = 0; i < MAX_PRODUCTS; i++) {
			System.out.println("Ingrese el nombre del producto " + (i +1) + ":");
			namesProducts[i] = input.nextLine();
			System.out.println("Ingrese la cantidad disponible ");
			int quantity = input.nextInt();
			
			while(quantity < 0 ) {
				System.out.print("Cantidad invalida, vuelve a intentar");
				quantity = input.nextInt();
			}
			
			quantityProducts[i] = quantity;
			totalProducts += quantity;
			input.nextLine();
			
		}
		
		//Menu time
		int option;
		do {
			System.out.println("-------Menu-------");
			System.out.println("Elija una opcion");
			System.out.println("1. Mostrar todos los productos y sus existencias ");
			System.out.println("2. Buscar un producto por nombre y ver su cantidad");
			System.out.println("3. Actualizar el inventario");
			System.out.println("4. Generar alerta de productos con una cantidad menor a 10");
			System.out.println("5. Salir");
			
			option = input.nextInt();
			input.nextLine();
			
			switch(option) {
			// Show inventory
			case 1:
				System.out.println("Inventario actual");
				totalProducts = 0;
				
				for(int i = 0 ; i< MAX_PRODUCTS; i++) {
					System.out.println(namesProducts[i] + "  "+ quantityProducts[i]);
					totalProducts += quantityProducts[i];
				}
				System.out.println("Total del inventario: " + totalProducts);
				break;
				
			//search 
			case 2:
				System.out.print("Ingrese el nombre del producto que quiera buscar");
				String search = input.nextLine();
				boolean found = false;
				for(int i = 0; i < MAX_PRODUCTS;  i++) {
					if(namesProducts[i].equalsIgnoreCase(search)) {
						System.out.println("El producto " + namesProducts[i] + " Tiene en stock de " + quantityProducts[i]);
						found = true;
						break;	
					}
				}
				if (!found) {
					System.out.println("Poducto no encontrado");
				}
				break;
				
			//upgrade inventory
			case 3:
				System.out.print("Ingrese el producto a actualizar: ");
				String upgrade = input.nextLine();
				found = false;
				
				for(int i = 0 ; i < MAX_PRODUCTS; i++) {
					if(namesProducts[i].equalsIgnoreCase(upgrade)) {
						System.out.println("Ingrese la cantidad a agregar (+) o quitar (-) ");
						
						int change = input.nextInt();
						int newQuantity = quantityProducts[i] + change;
						
						if (newQuantity < 0) {
							System.out.println("Invalido no puede tener una cantidad negativa");
						} else {
							quantityProducts[i] = newQuantity;
							System.out.println("inventario actualizado del producto " + namesProducts[i] + "Con la cantidad de: " + quantityProducts[i]);
						}
						found = true;
						break;
					}
				}
				if(!found) {
					System.out.println("Producto no encontrado");
				}
				input.nextLine();
				break;
			
			//low stock
			case 4:
				System.out.println("Productos con stock menor a 10: ");
				boolean alert = false;
				
				for(int i = 0 ; i < MAX_PRODUCTS; i++) {
					if(quantityProducts[i] < 10) {
						System.out.println("El producto " + namesProducts[i] + " Tiene en stock de " + quantityProducts[i]);
						alert = true;
					}
				}
				if(!alert) {
					System.out.println("No hay productos con bajo stock");
				}
				break;
				
			case 5:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción inválida.");
				
		
				
			}
			
			}while (option != 5);
	
		
		
		 input.close();
		
	}

}
