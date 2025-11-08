import styles from "./HomePage.module.css";
import logo from "../../assets/clear_info_1.png";
import {Card} from "primereact/card";
import {useAuth} from "../../keycloak/useAuth.ts";
import {Button} from "primereact/button";
import {useNavigate} from "react-router";

const HomePage = () => {

    const {isAuthenticated, getToken} = useAuth();
    const navigate = useNavigate();

    return <section className={`indentations ${styles.homePage}`}>
        <Card className={`flex_center card`}>
            <img className={styles.logo}
                src={logo}
                alt={`Лого clear vision`}
            />
        </Card>
        <Card className={`card`}>
                <span className={styles.accentColor}>Clear Vision</span> — это сервис для полного контроля над вашими финансами. Получайте сводку по доходам и расходам, планируйте крупные покупки, настраивайте автоматические платежи и гибко управляйте своим бюджетом. Планируйте будущее вместе с Clear Vision.
        </Card>
        <div className={`flex_center`}>
            <Button
                label={isAuthenticated ? "Перейти к финансам" : "Начать учитывать финансы"}
                className={`button`}
                onClick={() => navigate("/info")}
            />
        </div>
    </section>
}

export default HomePage;



