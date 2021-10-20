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
			<h2>Status reklamacji.</h2>
			<p>Sprawdź status swojej reklamacji.</p>
		</div>
	

	<form action="Sprawdzanie_statusu" method="post">

		<div class="form-row">
			<div class="form-group col-md-12">
				<label>Identyfikator reklamacji:</label> <input type="text"
					class="form-control" name="identyfikator"
					placeholder="Wpisz numer reklamacji łamany przez rok, np. 589/2019" required>
			</div>

		</div>
		<button type="submit" class="btn btn-primary btn-md">Sprawdź
			status</button>
	</form>



</div>





</body>
</html>