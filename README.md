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

## ▶️ Cómo ejecutar la aplicación

### 🔹 Android
Para compilar y ejecutar en Android Studio:
```bash
./gradlew :composeApp:assembleDebug
```

Esto genera un APK en:
```
composeApp/build/outputs/apk/debug/composeApp-debug.apk
```

También puedes abrir el proyecto en **Android Studio**, seleccionar la configuración `composeApp` y ejecutar en un emulador o dispositivo físico.

### 🔹 iOS

Para compilar y ejecutar en iOS:

1. Abre el proyecto en **Xcode** desde la carpeta [`/iosApp`](./iosApp).
2. Selecciona un simulador o dispositivo físico.
3. Ejecuta con `Run`.

También es posible compilar desde terminal:
```bash
./gradlew :composeApp:linkDebugFrameworkIosArm64
```

---

## 📱 Descargas de builds

Versión actual compilada y lista para instalar:

* **Android APK** 👉 [📥 Descargar APK](./composeApp/build/outputs/apk/release/composeApp-release-unsigned.apk)
* **iOS Framework** 👉 [📥 Descargar Framework](./composeApp/build/bin/iosArm64/debugFramework/ComposeApp.framework)

> **Nota para iOS**: Para instalar en un dispositivo iOS, necesitarás abrir el proyecto en Xcode y compilar desde ahí, ya que requiere firma digital de Apple Developer.

---

## ✨ Funcionalidades implementadas

* **Autenticación:** Pantalla de Login y Crear Cuenta con validación de formularios.
* **Dashboard:** Vista principal con temporizador Pomodoro (enfoque y descansos).
* **Estadísticas:** Visualización de métricas de productividad y gráficos.
* **Gestión de tareas:** Listado, creación y edición de tareas con categorías.
* **Ajustes:** Configuración de tiempos, notificaciones y preferencias del usuario.

Todas las pantallas son **navegables** mediante **Voyager Tabs** y cumplen el flujo definido en los mockups de Figma.

---

## ⚙️ Requisitos técnicos

* **Android Studio 2025.1.3** o superior
* **Kotlin Multiplatform plugin** actualizado
* **Xcode 15+** (para compilar en iOS)
* **Gradle 8.5+**
* **JDK 11+**

---

## 🎨 Design System

El proyecto implementa un sistema de diseño consistente basado en:

* **Paleta de colores:**
  - Primary: `#205375`
  - Primary Dark: `#112B3C`
  - Accent: `#F66B0E`
  - Background: `#FFFFFF`
  - Surface: `#F8F9FA`

* **Tipografía:** Material Design 3 Typography
* **Componentes:** Material 3 Components con customización
* **Navegación:** Voyager TabNavigator

---

## 📝 Notas de implementación

* El proyecto está construido siguiendo **Google Material Design 3**.
* La navegación se implementa con **Voyager** (`TabNavigator`).
* Se usa la **paleta de colores y design system** definido en Figma.
* Esta entrega corresponde a la fase de **maquetación funcional**, con navegación completa entre pantallas.
* **Corrección aplicada:** Se solucionó el error de compatibilidad con SVG en Android convirtiendo los iconos a formato PNG.

---

## 🚀 Instalación rápida

### Android
1. Descarga el APK desde el enlace de arriba
2. Habilita "Fuentes desconocidas" en tu dispositivo Android
3. Instala el APK

### iOS
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

## 🐛 Solución de problemas

**Error "Android platform doesn't support SVG format":** ✅ **Solucionado**
- Se convirtieron los archivos SVG a PNG para compatibilidad con Android.

**Build falla en iOS:**
- Asegúrate de tener Xcode 15+ instalado
- Verifica que tu cuenta de desarrollador esté configurada correctamente

---

*Desarrollado como parte del proyecto académico F.O.C.U.S. - 2025*
