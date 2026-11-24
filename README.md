# SkyDrop Delivery Rules: testing and CI/CD tutorial

A tiny plain-Java project (no Maven, no Gradle) for learning unit testing with JUnit 5,
code coverage, Git and GitHub, pull requests with code review, and CI/CD with GitHub Actions.

| Function | Rule |
|---|---|
| `canCarry(weightKg)` | more than 0 kg and at most 2.5 kg |
| `packageSize(weightKg)` | SMALL under 0.5 kg, MEDIUM under 1.5 kg, else LARGE |
| `deliveryFee(km, kg, express)` | $3 + $1.50 per km, +$2 over 1 kg, express x1.5 |
| `estimateMinutes(km)` | 40 km/h plus 2 minutes for take-off and landing, rounded up |
| `hasEnoughBattery(km, battery%)` | 5% per km for the round trip, plus a 10% reserve |

```
src/   DeliveryRules.java   the five rules
       App.java             prints a plan for one sample order
test/  DeliveryRulesTest.java   JUnit 5 tests
lib/   JUnit jar (added by VS Code: Testing view > Enable Java Tests)
```

## Run it in VS Code

You need JDK 17 or newer, Git, and VS Code with the **Extension Pack for Java**.

1. Open this folder in VS Code and wait for **Java: Ready**.
2. Open `src/App.java` and click **Run** above `main`.
3. Open the **Testing** view (flask icon) and click **Run Tests**.
4. Click **Run Tests with Coverage** to see which lines the tests reach.

Every push and pull request runs the tests on GitHub Actions (`.github/workflows/ci.yml`).
When `main` passes, the coverage report is published to GitHub Pages.
