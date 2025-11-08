import { BrowserRouter as Router, Navigate, Route, Routes } from 'react-router';
import styles from './App.module.css';
import {AuthProvider} from "./keycloak/AuthProvider.tsx";
import Header from "./modules/organisms/header/Header.tsx";
import Footer from "./modules/organisms/footer/Footer.tsx";
import HomePage from "./modules/pages/HomePage.tsx";
import ProtectedRoute from "./keycloak/ProtectedRoute.tsx";
import InfoPage from "./modules/pages/info/InfoPage.tsx";

function App() {

  return (
    <>
        <AuthProvider>
                <Router>
                    <div className={styles.wrapper}>
                        <Header />
                        <main className={styles.main}>
                            <Routes>
                                <Route path="/" element={<Navigate to="/home" />} />
                                <Route path="/home" element={<HomePage />} />
                                <Route element={<ProtectedRoute />}>
                                    <Route path={"/info"} element={<InfoPage />} />
                                </Route>
                            </Routes>
                        </main>
                        <Footer />
                    </div>
                </Router>
        </AuthProvider>
    </>
  )
}

export default App;



/*
<Route element={<ProtectedRoute />}>
<Route path="/stepByStep" element={<StepByStepPage />} />
 */
