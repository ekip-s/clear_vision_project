import AccountSelector from "../../templates/account_selector/AccountSelector.tsx";
import {useGetAccounts} from "../../../api/useGetAccounts.ts";

const InfoPage = () => {
    const {data, isLoading, error} = useGetAccounts();

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error.message}</div>;
    }

    return <div className={`indentations`}>
        <AccountSelector accounts={data}/>
    </div>
}

export default InfoPage;

