import AccountSelector from "../../templates/account_selector/AccountSelector.tsx";
import {useGetAccounts} from "../../../api/useGetAccounts.ts";
import {Card} from "primereact/card";
import LocalMenu from "../../molecules/local_menu/LocalMenu.tsx";
import OperationsList from "../../templates/operations/OperationsList.tsx";

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
        <div className={`flex justify_between`}>
            <Card className={`card width_100`}>

            </Card>
            <Card className={`card width_100`}>
                <LocalMenu />
            </Card>
        </div>
        <OperationsList />
    </div>
}

export default InfoPage;