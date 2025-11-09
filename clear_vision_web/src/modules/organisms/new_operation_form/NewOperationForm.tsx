import styles from "./NewOperationForm.module.css";
import type {Account} from "../../../api/model/AccountModel.ts";
import {type FormEvent, useState} from "react";
import {InputNumber} from "primereact/inputnumber";
import {Dropdown, type DropdownChangeEvent} from 'primereact/dropdown';
import {Button} from "primereact/button";
import { InputTextarea } from 'primereact/inputtextarea';
import {useCategory} from "../../../api/useCategory.ts";
import Loading from "../../molecules/loading/Loading.tsx";
import Error from "../../molecules/error/Error.tsx";
import {useCreateOperation} from "../../../api/useOperations.ts";
import type {Category} from "../../../api/model/CategoryModel.ts";

interface NewOperationFormProps {
    accounts: Account[];
    setVisible: (visible: boolean) => void;
}

const NewOperationForm = ({accounts, setVisible}:NewOperationFormProps) => {

    const account = accounts.find(account => account.type === "CURRENT_ACCOUNT");
    const [amount, setAmount] = useState<number | null | undefined>(null);
    const [category, setCategory] = useState<Category | null>(null);
    const {data, isLoading, error} = useCategory();
    const [description, setDescription] = useState<string>('');
    const [formError, setFormError] = useState<string | null>(null);
    const createOperation = useCreateOperation(account?.id);

    const addNewOperationHandler = (event:FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setFormError(null);
        createOperation.mutate(
            {
                amount,
                categoryId: category ? category.id : null,
                description
            },
            {
                onSuccess: () => {
                    setVisible(false);
                },
                onError: (error) => {
                    setFormError(error.message || 'Ошибка при создании операции');
                },
            }
        );
    }

    if (isLoading) {
        return <Loading />;
    }

    if (error) {
        return <Error message={error.message} />;
    }

    return <form className={styles.newOperationForm} onSubmit={addNewOperationHandler}>
        <div className={`flex justify_between align_center gap_5`}>
            <div className={styles.container1}>
                <label className={`block bold fs_sm`} htmlFor="input_number_amount">Сумма операции:</label>
                <InputNumber
                    inputId={'input_number_amount'}
                    inputClassName={`inputNumber`}
                    value={amount}
                    onValueChange={(e) => setAmount(e.value)}
                    mode="decimal"
                    min={-1000000}
                    max={1000000}
                    locale={'ru-RU'}
                    minFractionDigits={2}
                    maxFractionDigits={2}
                    useGrouping={true}
                    required={true}
                />
            </div>
            <div className={styles.container2}>
                <label htmlFor={`select_category_dropdown`} className={`block bold fs_sm`}>Категория:</label>
                <Dropdown
                    inputId={`select_category_dropdown`}
                    options={data}
                    optionLabel={"name"}
                    className={`dropdown ${styles.crossFix}`}
                    value={category}
                    onChange={(e: DropdownChangeEvent) => setCategory(e.value)}
                    showClear={true}
                />
            </div>
        </div>
        <div className={styles.textBlock}>
            <label htmlFor={`add_new_ops_description`} className={`block bold fs_sm`}>Комментарий:</label>
            <InputTextarea
                id={'add_new_ops_description'}
                className={styles.inputTextarea}
                maxLength={100}
                placeholder={"Описание операции"}
                value={description}
                onChange={(e) => setDescription(e.target.value)}
            />
            <div className="char-counter" style={{textAlign: 'right', fontSize: '0.85rem', color: '#666'}}>
                {description.length} / {100}
            </div>
        </div>
        <div className={`btn_block flex align_center justify_end`}>
            <Button
                disabled={!amount}
                label={"Создать операцию"}
                type="submit"
                className={`button`}
            />
        </div>
    </form>
}

export default NewOperationForm;
