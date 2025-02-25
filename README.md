# TaskFlow

TaskFlow to aplikacja do zarządzania zadaniami, pozwalająca na tworzenie tablic (boards) z zadaniami, które można edytować, usuwać oraz przesuwać w interfejsie przy użyciu biblioteki **React Beautiful DnD**. Aplikacja oferuje również funkcje rejestracji, logowania, wylogowywania, dodawania avatara użytkownika oraz możliwość przypisywania innych użytkowników do tablic.

<div style="display: flex; justify-content: space-between;">
    <img src="task_flowApp_screens/ss1.png" width="40%">
    <img src="task_flowApp_screens/ss2.png" width="40%">
    <img src="task_flowApp_screens/ss3.png" width="40%">
    <img src="task_flowApp_screens/ss4.png" width="40%">
    <img src="task_flowApp_screens/ss5.png" width="40%">
</div>

## Funkcjonalności

- **Rejestracja i logowanie** – użytkownicy mogą rejestrować się i logować do aplikacji.
- **Tworzenie tablic (boards)** – użytkownicy mogą tworzyć tablice do zarządzania zadaniami.
- **Edytowanie i usuwanie tablic** – użytkownicy mogą edytować i usuwać tylko swoje tablice.
- **Tworzenie zadań** – w obrębie tablic użytkownicy mogą dodawać zadania, które następnie mogą edytować i usuwać.
- **Edycja i usuwanie zadań** – użytkownicy mogą edytować lub usuwać zadania, ale tylko te, które sami dodali.
- **Przesuwanie zadań** – za pomocą **React Beautiful DnD** zadania można przeciągać między kolumnami.
- **Kalendarz** – za pomocą **React Calendar**, tablice z datami zakończenia są wyświetlane w kalendarzu.
- **Dodawanie avatara** – użytkownicy mogą dodać lub zmienić swoje zdjęcie profilowe.
- **Przypisywanie użytkowników do tablic** – użytkownicy mogą dodawać innych użytkowników do swoich tablic.
- **Wylogowanie** – użytkownicy mogą się wylogować z aplikacji.

## Technologie

- **Frontend**: React, Redux Toolkit, React Beautiful DnD, React Bootstrap
- **Backend**: Spring Boot
- **Baza danych**: H2 Database

## Instalacja

1. **Klonowanie repozytorium**
   Aby sklonować repozytorium, użyj polecenia:
   ```bash
   git clone https://github.com/kamilb13/task_flow-app-backend.git
- Dla frontendu:
   ```bash
   git clone https://github.com/kamilb13/task_flow-app-frontend.git
2. **Instalacja zależności**
Przejdź do katalogów frontend i backend, a następnie zainstaluj wymagane zależności.

3. **Frontend (React):**
   ```bash
    cd task_flow_app_frontend
    npm install
   
4. **Backend (Spring Boot):**
   ```bash
    cd task_flow_app_backend
    ./mvnw install

5. **Konfiguracja bazy danych**
Baza danych H2 jest wbudowana, ale można ją skonfigurować, edytując ustawienia w plikach konfiguracyjnych Spring Boot.

6. **Uruchomienie aplikacji**

- Frontend:
   ```bash
    cd task_flow_app_frontend
    npm start

- Backend:
   ```bash
    cd task_flow_app_backend
    ./mvnw spring-boot:run

Aplikacja będzie dostępna pod adresem http://localhost:3000 dla frontendu i http://localhost:8080 dla backendu (lub zgodnie z ustawieniami).

7. **Licencja** MIT

## Testy

<h2>AuthenticationControllerTest</h2>
<h3>testLoginWhenCredentialsAreValid</h3>
<p><strong>Cel:</strong> Sprawdzenie logowania przy prawidłowych danych.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 200 OK, Obecność tokena w odpowiedzi.</p>

<h3>testLoginWhenPasswordIsNotValid</h3>
<p><strong>Cel:</strong> Reakcja systemu na błędne hasło.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 401 Unauthorized</p>

<h3>testLoginWhenCredentialsAreNotValid</h3>
<p><strong>Cel:</strong> Reakcja na błędną nazwę użytkownika i hasło.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 401 Unauthorized</p>

<h3>testRegisterWhenUserDoesntExist</h3>
<p><strong>Cel:</strong> Rejestracja nowego użytkownika.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 201 Created</p>

<h3>testRegisterWhenUserExist</h3>
<p><strong>Cel:</strong> Rejestracja użytkownika, który już istnieje.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 409 Conflict</p><br>

<h2>TaskTest</h2>
<h3>testAddTask</h3>
<p><strong>Cel:</strong> Dodanie nowego zadania do tablicy.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 201 Created</p>

<h3>testDeleteTask</h3>
<p><strong>Cel:</strong> Usunięcie istniejącego zadania.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 200 OK, Zadanie usunięte z bazy danych.</p><br>

<h2>BoardTest</h2>
<h3>testCreateBoard</h3>
<p><strong>Cel:</strong> Tworzenie nowej tablicy użytkownika.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 201 Created, Poprawność nazwy tablicy i ID twórcy.</p>

<h3>testCreateBoardWithInvalidUser</h3>
<p><strong>Cel:</strong> Tworzenie tablicy z nieistniejącym użytkownikiem.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 400 Bad Request, Treść odpowiedzi: "Creator not found".</p>

<h3>testGetBoardsForUser</h3>
<p><strong>Cel:</strong> Pobieranie tablic przypisanych do użytkownika.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 200 OK, Lista tablic przypisanych do użytkownika.</p>

<h3>testGetBoardsForNonexistentUser</h3>
<p><strong>Cel:</strong> Pobieranie tablic dla nieistniejącego użytkownika.</p>
<p><strong>Weryfikacja:</strong> Kod odpowiedzi: 200 OK, Zwrócenie pustej listy.</p>


