<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
<title>Reklamacje</title>
</head>
<body style="background: #f0f0f5 !important">
	<div class="container">
		<div class="jumbotron" style="background: #D9E5F1 !important">
			<h2>Formularz zgłoszenia reklamacji</h2>
			<p>W celu złożenia reklamacji wypełnij poniższy formularz.
				Wszystkie pola są obowiązkowe.</p>
		</div>

		<form action="przesyl_danych" method="post">

			<div class="form-row">
				<div class="form-group col-md-6">
					<label>Imię:</label> <input type="text" class="form-control"
						name="imie" placeholder="Wpisz imię" required>
				</div>
				<div class="form-group col-md-6">
					<label>Nazwisko:</label> <input type="text" class="form-control"
						name="nazwisko" placeholder="Wpisz nazwisko" required>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-12">
					<label for="inputEmail4">E-mail:</label> <input type="email"
						class="form-control" name="email" placeholder="Wpisz e-mail" required>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-8">
					<label for="inputAddress">Ulica:</label> <input type="text"
						class="form-control" name="ulica" placeholder="Wpisz nazwę ulicy" required>
				</div>
				<div class="form-group col-md-4">
					<label for="inputAddress2">Nr domu i lokalu (opcjonalnie):</label>
					<input type="text" class="form-control" name="nr_zam"
						placeholder="np. 12/3, 154A, 45, etc." required>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-8">
					<label for="inputCity">Miasto:</label> <input type="text"
						class="form-control" name="miasto" placeholder="Wpisz miasto" required>
				</div>

				<div class="form-group col-md-4">
					<label for="inputZip">Kod pocztowy:</label> <input type="text"
						class="form-control" name="kod_pocztowy"
						placeholder="Wpisz kod pocztowy np. 12-345" required>
				</div>
			</div>
			<div class="form-group col-md-12">
				<label for="comment">Treść reklamacji:</label>
				<textarea class="form-control" rows="5" name="tresc_rek"
					placeholder="Wpisz treść reklamacji" required></textarea>
			</div>

			<button type="submit" class="btn btn-primary btn-md">Złóż
				reklamację</button>
		</form>
	</div>
</body>
</html>