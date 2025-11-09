import AccountSelector from "../../templates/account_selector/AccountSelector.tsx";
import {useAccounts} from "../../../api/useAccounts.ts";
import {Card} from "primereact/card";
import LocalMenu from "../../molecules/local_menu/LocalMenu.tsx";
import OperationsList from "../../templates/operations/OperationsList.tsx";

const InfoPage = () => {
    const {data, isLoading, error} = useAccounts();

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error || data === undefined) {
        return <div>Error: {data === undefined ? "В ответе нет данных" : error.message}</div>;
    }

    return <div className={`indentations`}>
        <AccountSelector accounts={data}/>
        <div className={`flex justify_between`}>
            <Card className={`card width_100`}>

            </Card>
            <Card className={`card width_100`}>
                <LocalMenu accounts={data} />
            </Card>
        </div>
        <OperationsList />
    </div>
}

export default InfoPage;