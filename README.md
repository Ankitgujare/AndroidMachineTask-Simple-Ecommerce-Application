# E-Commerce Android Application

## Overview
This is a mini e-commerce Android application that allows users to browse products, add items to a cart, and manage their cart locally. The app follows MVVM architecture and uses modern Android development practices.

## Features
- Browse products from FakeStore API
- View product details
- Add products to cart with quantity selection
- Manage cart items (update quantity, remove items)
- Local cart storage using Room Database
- Bottom navigation between Home and Cart screens

## Technical Implementation
- **Architecture**: MVVM (Model-View-ViewModel)
- **Networking**: Retrofit for API calls
- **Local Database**: Room for storing cart data
- **Dependency Injection**: Hilt
- **UI**: Jetpack Compose with Material Design
- **State Management**: LiveData and StateFlow
- **Image Loading**: Coil

## Dependencies
- Retrofit & Gson for REST API communication
- Room for local database
- Hilt for dependency injection
- Coil for image loading
- Jetpack Compose for UI
- Navigation Component for screen navigation

## How to Run
1. Clone the repository
2. Open the project in Android Studio
3. Build and run the project on an emulator or physical device

## API
The app uses the FakeStore API (https://fakestoreapi.com) to fetch product data.

## Database
The app uses Room Database to store cart items locally. The database has two tables:
1. `products` - Stores product information
2. `cart` - Stores cart items with quantity

## Project Structure
```
com.example.androidmachinetask/
├── data/
│   ├── local/          # Room Database and DAOs
│   ├── model/          # Data models (Product, CartItem)
│   ├── remote/         # API service
│   └── repository/     # Repository pattern implementation
├── di/                 # Dependency injection modules
├── navigation/         # Navigation setup
├── ui/
│   ├── components/     # Reusable UI components
│   ├── screens/        # Screen composables
│   └── theme/          # App theme
└── viewmodel/          # ViewModels
```

## Challenges Faced
- Dependencies version Conflicts and Finding the compatible dependencies ....


App Ui 
![Image Alt](https://github.com/Ankitgujare/AndroidMachineTask-Simple-Ecommerce-Application/blob/9a3c55ef74374c635b6de16b065255139d790bf7/Homescreen.jpeg)
![Image Alt](https://github.com/Ankitgujare/AndroidMachineTask-Simple-Ecommerce-Application/blob/9a3c55ef74374c635b6de16b065255139d790bf7/ProductDetailedScreen.jpeg)
![Image Alt](https://github.com/Ankitgujare/AndroidMachineTask-Simple-Ecommerce-Application/blob/9a3c55ef74374c635b6de16b065255139d790bf7/card.jpeg)
