export interface IndexData{
    title: string,
    picture_url: string,
    brief?: string,
    //ofkind: string,
    oftype?: string,
    key?: number,
    id?: string, 
    idElement?: string,
}
//专题结构
export interface SpecialStruct{
    id: number,
    showflag: number,
    ofname: string,
    resume: string,
    ids: string,
    picture_url: string,
    create_time: string,
    update_time: string,
}
//Kssindex
export interface OriStruct{
    id: string,
    showFlag: number,
    ofname: string,
    notes: string,
    resume: string,
    picture_url: string,
    createTime: string,
    updateTime: string,
}
//从服务端得到的介质实体
export interface ServerEntity{
    id: number,
    ofkind: string,
    resume: string,
    mainresponsible2: string,
    create_time: string,
    update_time: string,
    picture_url: string,
    file_url: string,
    ofname: string,
    keywords: string,
    mainresponsible1: string,
    secondresponsible: string,
    publisher: string,
    publishedplace: string,
    publishedtime: string,
    permission: string,
    oftype: string,
    notes: string,  //口述史专有属性
}
//本地上传服务端的实体
export interface HostEntity{
  id: number,
  keywords: string,  
  ofname: string,        
  ofkind: string,
  mainresponsible1: string, 
  secondresponsible: string,
  publisher: string,
  publishedplace: string,
  publishedtime: string,
  mainresponsible2: string,
  resume: string,
  permission: string,
}

export interface HostOriEntity extends HostEntity{
    oftype: string;
}

export class Article{
    id: number;
    ofkind: string;
    resume: string;
    mainresponsible2: string;
    create_time: string;
    update_time: string;
    //
    picture_url: string;
    file_url: string;
    ofname: string;
    oftype: string;
    //download: string;
    keywords: string;
    mainresponsible1: string;
    secondresponsible: string;
    publisher: string;
    publishedplace: string;
    publishedtime: string;
    permission: string;
    notes: string;
    constructor(notes, url, p_url, title, download, keyWord, mainResponsiblePerson, minorResponsiblePerson, publisher, placeOfPublication, data, numOfPages)
    {
        this.notes = notes; 
        this.picture_url = p_url;
        this.file_url = url;
        this.ofname = title;
        //this.download = download;
        this.keywords  = keyWord;
        this.mainresponsible1 = mainResponsiblePerson;
        this.secondresponsible = minorResponsiblePerson;
        this.publisher = publisher;
        this.publishedplace = placeOfPublication;
        this.publishedtime = data;
        this.permission = numOfPages;
    }
}