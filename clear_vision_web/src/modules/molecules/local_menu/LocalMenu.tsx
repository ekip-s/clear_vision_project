import {Toast} from "primereact/toast";
import {Menu} from "primereact/menu";
import styles from "./LocalMenu.module.css";
import type {MenuItem} from "primereact/menuitem";
import {useRef} from "react";

const LocalMenu = () => {
    const toast = useRef<Toast>(null);

    return <div>
        <Toast ref={toast} />
        <Menu
            model={items}
            className={styles.menu}
        />
    </div>
}

export default LocalMenu;

const items: MenuItem[] = [
    {
        label: 'Операции:',
        items: [
            {
                label: 'Новая операция',
                icon: 'pi pi-plus'
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