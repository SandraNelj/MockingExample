# ShoppingCart & Booking System – Laboration 2

## Projektöversikt

Detta projekt är en laboration i **Testdriven utveckling (TDD)** med fokus på:

- Enhetstester med **JUnit 5** och **AssertJ**
- Mocking av beroenden med **Mockito**
- Implementering av **ShoppingCart** och **BookingSystem**
- Refaktorering och **Dependency Injection** för testbarhet och underhållbarhet

Projektet innehåller tre huvudpaket:

- `shop` – ShoppingCart och Item
- `booking` – BookingSystem, Room, Booking
- `payment` – PaymentProcessor och relaterade beroenden

---

## Funktionalitet

### ShoppingCart

- Lägga till varor med kvantitet
- Ta bort varor
- Summera totalpris
- Applicera rabatter på varor
- Hantera uppdateringar av kvantitet
- Skydd mot felaktiga inputs (t.ex. negativ kvantitet eller null-objekt)

### BookingSystem

- Boka rum med start- och sluttid
- Kontrollera tillgänglighet
- Avboka bokningar med notifiering
- Mocking används för TimeProvider, RoomRepository och NotificationService

### PaymentProcessor

- Interface-baserad betalningshantering
- Simulerar betalningsgateway
- Sparar betalning i repository och skickar email-notifiering
- Dependency Injection används för testbarhet

---

## Teststrategi

- **TDD-cykel:** Red → Green → Refactor
- **Enhetstester:** Verifierar både lyckade och misslyckade scenarion
- **AssertJ** används konsekvent för läsbar och fluent assertion
- **Mockito** används för att skapa test doubles
- **Kanttester:**
    - Tom kundvagn
    - Lägg till 0 kvantitet
    - Lägg till negativ kvantitet
    - Ta bort icke-existerande vara
    - Null-check på Item

### Identifiera beroenden: 
**Ansvar: Betalning 
Ursprung: PaymentApi.charge 
Extraherat till: PaymentGateway**

**Ansvar: Persistens 
Ursprung: DatabaseConnection 
Extraherat till: PaymentRepository**

**Ansvar: Notifiering 
Ursprung: EmailService 
Extraherat till: EmailNotifier**

### Refaktoreringsbeslut:
Interface används för att möjliggöra mockning och framtida implementationer
Konstruktor-injection valdes för tydlighet och testbarhet
Statisk kod togs bort för att undvika dolda beroenden
Affärslogik isolerades från infrastruktur

PaymentProcessor har refaktorerats genom att extrahera externa beroenden till 
interface och injecera dem via konstruktor. 
