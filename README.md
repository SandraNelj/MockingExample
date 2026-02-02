Identifiera beroenden: 
Ansvar: Betalning, Ursprung: PaymentApi.charge, Extraherat till: PaymentGateway
Ansvar: Persistens, Ursprung: DatabaseConnection, Extraherat till: PaymentRepository
Ansvar: Notifiering, Ursprung: EmailService, Extraherat till: EmailNotifier
Ovan beroenden har extraherats till interface. 

Dependency Injection: 
Alla beroenden injeceras av konstuktor istället för att skapas internt, ökar testbarhet och gör koden flexibel.

Tester: 
Skrivt tester med Mockito, JUnit och AssertJ
Både lyckad och misslyckad betalning täcks av test

Refaktoreringsbeslut:
Interface används för att möjliggöra mockning och framtida implementationer
Konstruktor-injection valdes för tydlighet och testbarhet
Statisk kod togs bort för att undvika dolda beroenden
Affärslogik isolerades från infrastruktur


PaymentProcessor har refaktorerats genom att extrahera externa beroenden till interface och injecera dem via konstruktor. 
