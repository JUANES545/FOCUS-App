# F.O.C.U.S. – Kotlin Multiplatform App (Android & iOS)

Este repositorio contiene la implementación de la aplicación **F.O.C.U.S.** (Flexible Organizer for Concentration Using Schedules), desarrollada con **Kotlin Multiplatform** y **Compose Multiplatform UI**.
La aplicación comparte una misma base de código para Android e iOS, incluyendo el sistema de diseño, navegación y pantallas definidas en Figma.

---

## 📂 Estructura del proyecto

- **[/composeApp](./composeApp/src)**
  Código compartido entre las apps Android e iOS.
  - [`commonMain`](./composeApp/src/commonMain/kotlin) → lógica y UI común.
  - [`androidMain`](./composeApp/src/androidMain/kotlin) → código específico de Android.
  - [`iosMain`](./composeApp/src/iosMain/kotlin) → código específico de iOS.

- **[/iosApp](./iosApp/iosApp)**
  Contiene el *entry point* de la aplicación iOS. Aquí se integran los componentes de Compose Multiplatform con SwiftUI y Xcode.

---

## ▶️ Cómo ejecutar el proyecto

### 📱 Plataformas Soportadas

Esta aplicación está desarrollada en **Kotlin Multiplatform** y puede ejecutarse en:
- **Android** (APK)
- **iOS** (Framework)

> **💡 Recomendación**: Para una experiencia completa, es recomendable compilar el proyecto en **Mac**, ya que permite compilar tanto Android como iOS. En **Windows** solo se puede compilar la parte de Android.

### 🛠️ Requisitos del Sistema

#### Para Android:
- **Android Studio**: Versión **Narwhal 3 Feature Drop 2025.1.3** o superior
- **JetBrains Toolbox**: Recomendado para manejar diferentes versiones de Android Studio
- **Extensiones necesarias**:
  - Kotlin Multiplatform
  - Compose Multiplatform

#### Para iOS (solo en Mac):
- **Xcode**: Versión 15+ instalado
- **Extensiones necesarias**:
  - Kotlin Multiplatform
  - Compose Multiplatform

### 🚀 Instrucciones de Compilación

#### 🔹 Android
Para compilar y ejecutar en Android Studio:
```bash
./gradlew :composeApp:assembleDebug
```

Esto genera un APK en:
```
composeApp/build/outputs/apk/debug/composeApp-debug.apk
```

También puedes abrir el proyecto en **Android Studio**, seleccionar la configuración `composeApp` y ejecutar en un emulador o dispositivo físico.

#### 🔹 iOS (solo en Mac)

Para compilar y ejecutar en iOS:

1. Abre el proyecto en **Xcode** desde la carpeta [`/iosApp`](./iosApp).
2. Selecciona un simulador o dispositivo físico.
3. Ejecuta con `Run`.

También es posible compilar desde terminal:
```bash
./gradlew :composeApp:linkDebugFrameworkIosArm64
```

### 📹 Video Tutorial

> **🎥 Configuración y demo de la App Android/iOS**

[![Demo de la App](https://img.youtube.com/vi/h3dxaNwxlfg/0.jpg)](https://youtu.be/h3dxaNwxlfg)


---

## 📱 Descargas de builds

Versión actual compilada y lista para instalar:

* **Android APK** 👉 [📥 Descargar APK](https://github.com/JUANES545/FOCUS-App/raw/refs/heads/master/APK/FOCUS%20App.apk)
* **iOS Framework** 👉 [📥 Descargar Framework](.composeApp/build/xcode-frameworks/Debug/iphonesimulator26.0/ComposeApp.framework)

> **Nota para iOS**: Para instalar en un dispositivo iOS, necesitarás abrir el proyecto en Xcode y compilar desde ahí, ya que requiere firma digital de Apple Developer.

---

## ✨ Funcionalidades implementadas

* **Autenticación:**
  - Pantalla de Login con validación de formularios
  - Pantalla de Crear Cuenta (Registro) con validación completa
  - Pantalla de Recuperar Contraseña con envío de enlace
* **Dashboard:** Vista principal con temporizador Pomodoro (enfoque y descansos).
* **Estadísticas:** Visualización de métricas de productividad y gráficos.
* **Gestión de tareas:**
  - Listado de tareas existentes
  - Pantalla de Crear Nueva Tarea con campos completos
  - Categorización y fechas de entrega
* **Ajustes:** Configuración de tiempos, notificaciones y preferencias del usuario.

Todas las pantallas son **navegables** mediante **Voyager Tabs** y cumplen el flujo definido en los mockups de Figma.

---

## ⚙️ Requisitos técnicos

* **Android Studio**: Narwhal 3 Feature Drop 2025.1.3 o superior
* **JetBrains Toolbox**: Para manejar diferentes versiones de Android Studio
* **Extensiones necesarias**:
  - Kotlin Multiplatform
  - Compose Multiplatform
* **Xcode 15+** (solo en Mac, para compilar iOS)
* **Gradle 8.5+**
* **JDK 11+**

---

## 🚀 Instalación rápida

### Android
1. Descarga el APK desde el enlace de arriba
2. Habilita "Fuentes desconocidas" en tu dispositivo Android
3. Instala el APK

### iOS (solo en Mac)
1. Abre el proyecto en Xcode
2. Conecta tu dispositivo iOS
3. Selecciona tu dispositivo y ejecuta (requiere cuenta de desarrollador)

---

## 📖 Recursos

* [Documentación oficial Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
* [Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform)
* [Voyager Navigation](https://github.com/adrielcafe/voyager)
* [Material Design 3](https://m3.material.io/)

---

*Desarrollado como parte del proyecto académico F.O.C.U.S. - 2025*
