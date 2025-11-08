import {useState} from "react";
import {Carousel} from "primereact/carousel";
import styles from "./AccountSelector.module.css";
import AccountCard from "../../molecules/account_card/AccountCard.tsx";
import type {Account} from "../../../api/model/AccountModel.ts";

interface AccountSelectorProps {
    accounts: Account[] | undefined;
}

const AccountSelector = ({accounts}:AccountSelectorProps) => {

    const [page, setPage] = useState(0);

    if (!accounts || accounts.length === 0) {
        return <div>No accounts available</div>;
    }

    console.log(accounts);

    return (
        <Carousel
            value={accounts}
            itemTemplate={(account) => <AccountCard account={account} />}
            numScroll={4}
            numVisible={4}
            responsiveOptions={responsiveOptions}
            page={page}
            onPageChange={(e) => setPage(e.page)}
            showNavigators={true}
            showIndicators={false}
            className={styles.carousel}
            circular={false}
        />
    );
}

export default AccountSelector;

const responsiveOptions = [
    {
        breakpoint: '1024px',
        numVisible: 3,
        numScroll: 3
    },
    {
        breakpoint: '768px',
        numVisible: 2,
        numScroll: 2
    },
    {
        breakpoint: '560px',
        numVisible: 1,
        numScroll: 1
    }
];