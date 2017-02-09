import java.util.Scanner;
/**
 * Classe de test de l'algorithme de remboursement d'un distributeur de boissons
 * Le remboursement privilégie les pièces les plus petites en premier
 * @author hugo Vaillant
 *
 */
public class TestRemboursementMonnaie {
	/**
	 * Méthode main
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choixBoisson, montantInsere, prixCentimeBoisson, montantCentimeARembourser, sommeMonnaieMachine;
		Boisson boissonChoisie;
		int montantCentimeCagnotte=0;
		int memoirePiece=0;
		
		//initialisation du tableau de pièces de la machine (ex 200centimes = 2€)
		int[][] tabPiecesMachine = {
				{1,2},
				{2,2},
				{5,2},
				{10,4},
				{20,2},
				{50,1},
				{100,1},
				{200,1},
		};
		
		//initialisation du tableau de pièces tampon
		int[][] tabPiecesBuffer = {
				{1,0},
				{2,0},
				{5,0},
				{10,0},
				{20,0},
				{50,0},
				{100,0},
				{200,0},
		};
		
		Boisson b1 = new Boisson("Red-Bull", 150);
		Boisson b2 = new Boisson("Café", 40);
		Boisson b3 = new Boisson("Eau-plate", 100);
		
		System.out.println("Quelle boisson voulez-vous ?");
		System.out.println("1 - Red-Bull (1,5€)");
		System.out.println("2 - Café (0,40€)");
		System.out.println("3 - Eau-plate (1€)");
		choixBoisson = sc.nextInt();
		
		System.out.println("Indiquez le montant insérée (en centimes) :");
		montantInsere = sc.nextInt();
		sc.close();
		
		if(choixBoisson == 1) {
			boissonChoisie = b1;
		}else if (choixBoisson == 2) {
			boissonChoisie = b2;
		}else if (choixBoisson == 3) {
			boissonChoisie = b3;
		}else {
			boissonChoisie = b1;
		}
		prixCentimeBoisson = boissonChoisie.getPrix();
		montantCentimeARembourser = montantInsere - prixCentimeBoisson;		
		sommeMonnaieMachine = calculSommeMonnaieMachine(tabPiecesMachine);
		
		if(montantCentimeARembourser > sommeMonnaieMachine){
			System.out.println("La machine n'as pas assez d'argent pour vous rembourser : annulation de la transaction");
			System.exit(1);
		}else if(montantCentimeARembourser == 0){
			System.out.println("Compte rond : pas de monnaie à rembourser");
			System.exit(1);
		}else if(montantCentimeARembourser < 0){
			System.out.println("Vous n'avez pas assez d'argent !");
			System.exit(1);
		}
		
		//Parcours du tableau de pièces et ajout en partant de la plus petite
		for (int i = 0; i < tabPiecesMachine.length; i++) {
			while (montantCentimeCagnotte < montantCentimeARembourser && tabPiecesMachine[i][1] > 0) {
				montantCentimeCagnotte += tabPiecesMachine[i][0];
				tabPiecesBuffer[i][1]++;
				tabPiecesMachine[i][1]--;
			}
			if(montantCentimeCagnotte >= montantCentimeARembourser){
				memoirePiece=i;
				break;
			}
		}
		//Parcours du tableau de pièces dans le sens inverse pour enlever le surplus monétaire
		if(montantCentimeCagnotte == montantCentimeARembourser){
			afficherPieces(tabPiecesBuffer);
			System.exit(0);
		}else{
			for (int i = memoirePiece-1; i>-1 ; i--) {
				while (montantCentimeCagnotte > montantCentimeARembourser && tabPiecesBuffer[i][1] > 0 && (tabPiecesBuffer[i][0] <= (montantCentimeCagnotte-montantCentimeARembourser))) {
					montantCentimeCagnotte -= tabPiecesBuffer[i][0];
					tabPiecesBuffer[i][1]--;
					tabPiecesMachine[i][1]++;
				}
			}
		}
		afficherPieces(tabPiecesBuffer);
		System.exit(0);
	}

	/**
	 * 
	 * @param tabPieces : tableau de pièces à afficher
	 */
	private static void afficherPieces(int[][] tabPieces) {
		float valeurPieceEuros;
		System.out.println("REMBOURSEMENT :");
		for (int i = 0; i < tabPieces.length; i++) {
			valeurPieceEuros = (float) tabPieces[i][0]/100;
			System.out.println(tabPieces[i][1] + " Pièce(s) de "+ valeurPieceEuros + "€");
		}
	}

	/**
	 * Calcul de la valeur de la somme des pièces d'un tableau
	 * @param tabPieces : tableau de pièces
	 * @return montant de la somme des pièces du tableau
	 */
	private static int calculSommeMonnaieMachine(int[][] tabPieces) {
		int montant=0;
		for (int i = 0; i < tabPieces.length; i++) {
			montant+=(tabPieces[i][0]*tabPieces[i][1]);
		}
		return montant;
	}

}
