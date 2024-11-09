<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.io.IOException" %>

<%
    // Initialize variables for user inputs and results
    String loanAmountStr = request.getParameter("loanAmount");
    String annualInterestRateStr = request.getParameter("annualInterestRate");
    String loanTermInYearsStr = request.getParameter("loanTermInYears");
    double monthlyPayment = 0.0;
    boolean isCalculated = false;

    // Perform calculation if all form parameters are provided
    if (loanAmountStr != null && annualInterestRateStr != null && loanTermInYearsStr != null) {
        try {
            double loanAmount = Double.parseDouble(loanAmountStr);
            double annualInterestRate = Double.parseDouble(annualInterestRateStr);
            int loanTermInYears = Integer.parseInt(loanTermInYearsStr);

            // Convert annual interest rate to monthly and loan term to months
            double monthlyInterestRate = annualInterestRate / 100 / 12;
            int loanTermInMonths = loanTermInYears * 12;

            // Calculate the monthly payment
            monthlyPayment = (loanAmount * monthlyInterestRate) /
                             (1 - Math.pow(1 + monthlyInterestRate, -loanTermInMonths));

            isCalculated = true;
        } catch (NumberFormatException e) {
            // Handle invalid input
            out.println("<p style='color:red;'>Please enter valid numeric values.</p>");
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Car Loan Calculator</title>
</head>
<body>
    <h2>Car Loan Calculator</h2>

    <!-- Loan Calculator Form -->
    <form method="get" action="carLoanCalculator.jsp">
        <label for="loanAmount">Loan Amount:</label>
        <input type="number" id="loanAmount" name="loanAmount" value="<%= loanAmountStr != null ? loanAmountStr : "" %>" required step="0.01"><br><br>

        <label for="annualInterestRate">Annual Interest Rate (%):</label>
        <input type="number" id="annualInterestRate" name="annualInterestRate" value="<%= annualInterestRateStr != null ? annualInterestRateStr : "" %>" required step="0.01"><br><br>

        <label for="loanTermInYears">Loan Term (years):</label>
        <input type="number" id="loanTermInYears" name="loanTermInYears" value="<%= loanTermInYearsStr != null ? loanTermInYearsStr : "" %>" required><br><br>

        <button type="submit">Calculate</button>
    </form>

    <!-- Display Result if Calculated -->
    <% if (isCalculated) { %>
        <h3>Car Loan Monthly Payment</h3>
        <p>Your estimated monthly payment is: $<strong><%= new DecimalFormat("#.##").format(monthlyPayment) %></strong></p>
    <% } %>
</body>
</html>
