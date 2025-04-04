# Technical Challenge - List-Detail App using GraphQL

This repository contains the solution to a technical challenge, which involves building an Android application with the following features:

* **List-Detail View:** Displaying data retrieved from a remote GraphQL API.
* **Caching:** Implementing data caching for improved performance.
* **Infinite Scroll:** Loading more data as the user scrolls.
* **Gender Filtering:** Allowing users to filter the list by gender.
* **Favorites:** Enabling users to add items to a local storaged favorites.
* **Deeplinks:** Supporting deeplinks for navigation to detail pages.

## Technologies Used

* **Apollo Client:** For GraphQL data retrieval.
* **SQLDelight:** For local data persistence of favorites.
* **Koin:** For dependency injection.
* **Coil:** For asynchronous image loading.
* **JUnit 5:** For unit testing.
* **Mockk:** For mocking dependencies in unit tests.
* **Turbine:** For testing flows.

## Architecture

The application follows a Clean Architecture approach, divided into **presentation** / **domain** / **data** layers and the modules:

* **app:** The entry point of the application and handles navigation.
* **presentation:** The UI module, responsible for displaying data and handling user interactions using MVVM.
* **domain:** Contains the core business logic, models, use cases, and repositories.
* **data:** Implements the repositories, data sources, and interacts with external libraries like Apollo Client and SQLDelight.
* **build-logic:** Exposes convention plugins for consistent build configurations across modules.
* **lang:** A utility module for generic code shared across modules.
* **shared-test:** Contains test fixtures and utility functions used in unit testing across all layers.

### Data Flow

The presentation layer utilizes `StateFlow` to manage the UI state, combining data from two primary sources:

1.  **List Data:** Fetched from the GraphQL API or the in memory cache using use cases triggered by user events (e.g., reaching the end of the list).
2.  **Local Favorites:** Data from the SQLDelight database, reflecting changes in the user's favorites list.

### Caching

Apollo Client's caching mechanism is used for data caching. Additionally, a custom `InMemoryCache` (`CharacterLocalDatasource.kt`) is implemented to demonstrate caching principles without relying heavily on external libraries.

## Testing

Currently, only the following tests are implemented:

* **InjectionTest:** Verifies dependency injection across all modules.
* **ListViewModelTest:** Tests the `ListViewModel` logic, covering flow testing and other unit testing concepts.

The testing strategy focuses on mocking dependencies and using fixtures to represent realistic scenarios. It prioritizes checking final states over verifying specific method calls, promoting maintainability and avoiding white-box testing.
