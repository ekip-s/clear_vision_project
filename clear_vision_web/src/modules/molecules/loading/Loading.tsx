import styles from './Loading.module.css';

const Loading = () => {
    return (
        <div className={styles.container}>
            <div className={styles.spinner} />
            <span className={styles.text}>Загрузка...</span>
        </div>
    );
};

export default Loading;