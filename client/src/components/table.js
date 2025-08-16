import TableRow from '../components/table_row.js';

export default function Table(){

    let tableInfo = {
        headers : [
            "test1",
            "test2",
            "test3",
        ],
        rowInfo : {
            rowSize : 3
        }
    }

    const tableHeaders = [];
    const tableContents = [];

    for(let header in tableInfo.headers){
        tableHeaders.push(<span className='flex-auto' >{header}</span>);
    }

    tableContents.push(<TableRow tableRowInfo={tableInfo.rowInfo}/>);

    return(
        <div className="table border-2 border-black">
            <div className="tableHeaders flex flex-row divide-x divide-white bg-black text-white text-center">{tableHeaders}</div>
            {tableContents}
        </div>
    );
}