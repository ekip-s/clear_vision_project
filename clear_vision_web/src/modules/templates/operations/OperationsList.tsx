import {useOperations} from "../../../api/useOperations.ts";
import {useState} from "react";
import {Paginator, type PaginatorPageChangeEvent} from "primereact/paginator";
import Loading from "../../molecules/loading/Loading.tsx";
import Error from "../../molecules/error/Error.tsx";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import {formatDate} from "../../../api/date/dateFnk.ts";

const OperationsList = () => {

    const [first, setFirst] = useState<number>(0);
    const size = 10;

    const { data, isLoading, error } = useOperations({
        page: first/size,
        size,
    });

    const onPageChange = (event: PaginatorPageChangeEvent) => {
        setFirst(event.first);
    };

    if (isLoading) {
        return <Loading />;
    }

    if (error) {
        return <Error message={error.message} />;
    }

    if (!data || !data.content || data.content.length === 0) {
        return <div>Еще нет операций</div>
    }

    return <section className={`width_100`}>
        <h3>Операции:</h3>
        <div className={`width_100`}>
            <DataTable value={data.content} tableStyle={{ minWidth: '50rem' }}>
                <Column
                    field="createdAt"
                    header="Дата:"
                    body={(rowData) => formatDate(rowData.createdAt)}
                />
                <Column
                    field={`amount`}
                    header={`Сумма:`}
                    body={(rowData) => (
                        <div>
                            {rowData.amount} {rowData.currency}
                        </div>
                    )}
                />
                <Column
                    field={`categoryName`}
                    header={`Категория:`}
                />
                <Column
                    field={`accountName`}
                    header={`Счёт:`}
                />
                <Column
                    field={`description`}
                    header={`Описание:`}
                />
            </DataTable>
        </div>
        <div>
            <Paginator
                first={first}
                rows={size}
                totalRecords={data!.page.totalElements}
                onPageChange={onPageChange}
                template={{ layout: 'PrevPageLink CurrentPageReport NextPageLink' }}
            />
        </div>
    </section>
}

export default OperationsList;