import {Card} from "primereact/card";
import styles from "./AccountCard.module.css";
import type {Account} from "../../../api/model/AccountModel.ts";

interface AccountCardProps {
    account: Account;
}

const AccountCard = ({ account }: AccountCardProps) => {
    return <Card className={`${styles.accountCard}`}>
        <div className="flex flex-col">
            <div className={`flex justify-between`} style={{ height: '50px' }}>
                <div>
                    <div>
                        <h3>{account.name}</h3>
                    </div>
                </div>
                <div>{getType(account.type)}</div>
            </div>
            <div className={`flex justify-between`} style={{ height: '50px' }}>
                <div></div>
                <div className={`label_box`}>
                    <label>Баланс:</label>
                    <p className={`${account.balance > 0 ? styles.positively : styles.negative}`}>
                        <i className={`pi ${account.balance > 0 ? "pi-sort-amount-up" : "pi-sort-amount-down"}`}/> {getBalance(account.balance, account.currency)}
                    </p>
                </div>
            </div>
        </div>
    </Card>
}

export default AccountCard;

const getBalance = (balance: number, currency: string):string => {
    return `${balance} ${getCurrency(currency)}`;
}

const getCurrency = (currency: string): string => {
    if(currency == "RUB") {
        return "₽";
    } else {
        return currency
    }
}

const getType = (type:string):string => {
    if (type == "CURRENT_ACCOUNT") {
        return "";
    } else {
        return "";
    }
}