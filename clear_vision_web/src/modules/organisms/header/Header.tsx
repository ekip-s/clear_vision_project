import styles from "./Header.module.css";

const Header = () => {
    return <header className={styles.header}>
        <div className={`wrapper indentations`}>
            <div className={styles.logo}>
                <a href={`/home`}>Clear Vision</a>
            </div>
            <div></div>
            <div></div>
        </div>
    </header>
}

export default Header;