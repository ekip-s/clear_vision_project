import styles from './Error.module.css';

type ErrorProps = {
    message: string;
};

const Error = ({ message }: ErrorProps) => {
    return (
        <div className={styles.error}>
            <p>{message}</p>
        </div>
    );
};

export default Error;