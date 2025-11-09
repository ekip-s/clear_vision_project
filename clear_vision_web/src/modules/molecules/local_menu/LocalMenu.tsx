import {Toast} from "primereact/toast";
import {Menu} from "primereact/menu";
import styles from "./LocalMenu.module.css";
import type {MenuItem} from "primereact/menuitem";
import {useRef, useState} from "react";
import {Dialog} from "primereact/dialog";
import NewOperationForm from "../../organisms/new_operation_form/NewOperationForm.tsx";
import type {Account} from "../../../api/model/AccountModel.ts";

interface LocalMenuProps {
    accounts: Account[];
}

const LocalMenu = ({accounts}:LocalMenuProps) => {
    const toast = useRef<Toast>(null);
    const [visible, setVisible] = useState(false);

    const items: MenuItem[] = [
        {
            label: 'Операции:',
            items: [
                {
                    label: 'Новая операция',
                    icon: 'pi pi-plus',
                    command: () => {
                        setVisible(true);
                    }
                },
                {
                    label: 'Перевод',
                    icon: 'pi pi-credit-card'
                },
                {
                    label: 'Регулярные платежи',
                    icon: 'pi pi-arrow-right-arrow-left'
                }
            ]
        },
        {
            label: 'Другое:',
            items: [
                {
                    label: 'Корзина',
                    icon: 'pi pi-shopping-cart'
                }
            ]
        },
        {
            label: 'Статистика:',
            items: [
                {
                    label: 'Детальная статистика',
                    icon: 'pi pi-chart-line'
                }
            ]
        }
    ];

    return <div>
        <Toast ref={toast} />
        <Menu
            model={items}
            className={styles.menu}
        />
        <Dialog
            visible={visible}
            header={`Новая операция`}
            position="center"
            onHide={() => setVisible(false)}
            className={styles.dialogContent}
            headerClassName={styles.dialogContentHeader}
        >
            <NewOperationForm accounts={accounts} setVisible={setVisible} />
        </Dialog>
    </div>
}

export default LocalMenu;