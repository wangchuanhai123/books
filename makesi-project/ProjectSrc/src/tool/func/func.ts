const fileFiedOption = [
  {
    label: '关键字',
    field: 'keywords',
  },
  {
    label: '所属专题',
    field: 'ofkind',
  },
  {
    label: '主要责任者1',
    field: 'mainresponsible1',
  },
  {
    label: '主要责任者2',
    field: 'mainresponsible2'
  },
  {
    label: '次要责任者',
    field: 'secondresponsible'
  },
  {
    label: '出版者',
    field: 'publisher'
  },
  {
    label: '出版地',
    field: 'publishedplace'
  },
  {
    label: '出版时间',
    field: 'publishedtime'
  },
  {
    label: '权限',
    field: 'permission'
  },
  {
    label: '简介',
    field: 'resume'
  } 
];
//文件类型选择
//const uploadFileType = ref('')
//const uploadFileSpec = ref('')
const options = [
  {
    value: '图书',
    label: '图书',
  },
  {
    value: '报纸',
    label: '报纸',
  },
  {
    value: '档案',
    label: '档案',
  },
  {
    value: '期刊',
    label: '期刊',
  },
  {
    value: '音频',
    label: '音频',
  },
  {
    value: '图片',
    label: '图片',
  },
  {
    value: '视频',
    label: '视频',
  },
]
const optionsKinds = [
  //{
    //value: '口述史资料',
    //label: '口述史资料'
  //},
  {
    value: '文献资料',
    label: '文献资料',
  },
  {
    value: '学术研究',
    label: '学术研究'
  },
  //{
    //value: '特色专题研究',
    //label: '特色专题研究'
  //}
]

const allOptionsKinds = [
  {
    value: '口述史资料',
    label: '口述史资料'
  },
  {
    value: '文献资料',
    label: '文献资料',
  },
  {
    value: '学术研究',
    label: '学术研究'
  },
  {
    value: '特色专题研究',
    label: '特色专题研究'
  }
]
const optionPermission = [
  {
    value:  "0",
    label: '私密'
  },
  {
    value: "1",
    label: '半公开'
  },
  {
    value: "2",
    label: '公开'
  }
]

const kindsMap = ['图书', '报纸','档案', '期刊', '音频', '图片', '视频', '专题库', '所有', '口述史']
export const kindsbackMap = new Map();
for(let i = 0; i < kindsMap.length; i++){
  kindsbackMap.set(kindsMap[i], i);
}

export { kindsMap, options, optionPermission, optionsKinds, fileFiedOption, allOptionsKinds};