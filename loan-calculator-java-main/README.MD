# Android Loan Calculator

This is an Android application designed to calculate and manage loan repayment schedules. Supports both annuity and linear repayment methods, offering detailed monthly tracking and data export capabilities.

## Features

* **Dual Repayment Methods:** Choose between Annuity and Linear calculation options.
* **Flexible Term Configurations:** Input loan amount, annual interest rate, and duration (years and months).
* **Grace Periods:** Apply a deferral period with customized interest calculations and extended terms.
* **Date Filtering:** Set a specific starting date and filter repayment schedules.
* **Data Export:** Download and save the detailed monthly breakdown schedules locally in CSV format.

## Usage Instructions


1. **Download via ADB:** Use the command ``./gradlew installDebug``
1. **Launch the Application:** Open the app on your Android device.
1. **Enter Loan Details:** Input the total loan amount, annual interest rate, and full term length (years and months).
1. **Select Repayment Type:** Choose either **Annuity** or **Linear** calculation.
1. **Configure Advanced Settings (Optional):**
   * Select a custom starting date (defaults to the current date).
   * Define specific date filters to narrow down the visible schedule.
   * Configure a deferral/grace period along with its respective interest handling and extra term configurations.
1. **Generate Schedule:** Tap the **Calculate** button to view the complete schedule analysis:
   * View the comprehensive total amount to pay and total interest accumulated over the life of the loan.
   * Browse through an interactive, month-by-month schedule displaying individual payment totals, interest portions, and the remaining principle balance.
1. **Export Results:** Tap **Download** to export and save the generated repayment schedule as a CSV file.
