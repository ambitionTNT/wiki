<template>
  <div class="doc">
    <a-layout>

      <a-layout-content
          :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <a-row :gutter="24">
          <a-col :span="8">
            <p>
              <a-form
                  layout="inline"
                  :model="param"
              >
                <a-form-item>
                  <a-input v-model:value="param.name" placeholder="名称">
                  </a-input>
                </a-form-item>
                <a-form-item>
                  <a-button type="primary" size="small" danger @click="handleQuery()"
                  >
                    查询
                  </a-button>
                </a-form-item>
                <a-form-item>
                  <a-button type="primary" size="small" danger @click="add()"   >
                    新增
                  </a-button>
                </a-form-item>

              </a-form>




            </p>
            <br>
            <a-table
                v-if="level1.length > 0"
                :columns="columns"
                :row-key="record => record.id"
                :data-source="level1"
                :pagination="false"
                :loading="loading"
                :treeDefaultExpandAll="true"
                :tree-data="treeSelectData"
                size="small"
            >

              <template #name = "{ record,text }">
                {{record.sort}} {{text}}
              </template>

              <!--             eslint-disable-next-line-->
              <template v-slot:action="{ record,text }">
                <a-space size="small">
                  <a-button type="primary" size="small" @click="edit(record)" >
                    编辑

                  </a-button>

                  <a-popconfirm
                      title="删除后不可回复，您确认要删除?"
                      ok-text="Yes"
                      cancel-text="No"
                      @confirm="showConfirm(record.id)"

                  >
                    <a-button type="danger" size="small" >
                      删除
                    </a-button>
                  </a-popconfirm>

                </a-space>
              </template>
            </a-table>
          </a-col>
          <a-col :span="16">
            <p>
              <a-form
                  layout="inline"
                  :model="param">
                <a-form-item>
                  <a-button type="primary" @click="handleSave()">
                    保存
                  </a-button>
                </a-form-item>
              </a-form>
            </p>
            <a-form :model="doc" :label-col="{span : 6}" layout="vertical" >

              <a-form-item
                           :rules="[{ required: true, message: '名称不能为空!' }]"
              >
                <a-input v-model:value="doc.name" placeholder="请输入名称" />
              </a-form-item>
              <a-form-item
              >
                <a-tree-select
                    :tree-data="treeSelectData"
                    v-model:value="doc.parent"
                    style="width: 100%"
                    :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                    placeholder="父文档"
                    :treeDefaultExpandAll="true"

                    tree-default-expand-all
                    :fieldNames="{
              label:'name',
              children: 'children',
              value: 'id',
              }"
                    tree-node-filter-prop="title"
                >

                </a-tree-select>
              </a-form-item>
              <!--        <a-form-item label="父文档">

                          <a-select

                              v-model:value="doc.parent"
                              ref="select"
                          >
                            <a-select-option :value="0">
                            </a-select-option>
                            <a-select-option v-for="c in level1"
                                             :key="c.id"
                                             :value="c.id"
                                             :disabled="doc.id===c.id">
                              {{c.name}}
                            </a-select-option>
                          </a-select>
                      </a-form-item>-->

              <a-form-item >
                <a-input v-model:value="doc.sort"
                         type="text"
                         placeholder="顺序"
                />
              </a-form-item>

              <a-form-item>
                <a-button type="primary" size="small" danger @click="handlePreviewContent()"
                >
                  内容预览
                </a-button>
              </a-form-item>


              <a-form-item label="内容">
                <div style="border: 1px solid #ccc">
                  <Toolbar
                      style="border-bottom: 1px solid #ccc"
                      :editor="editorRef"
                      :defaultConfig="toolbarConfig"
                      :mode="mode"
                  />
                  <div id="content">
                    <Editor
                        style="height: 500px; overflow-y: hidden;"
                        v-model="valueHtml"
                        :defaultConfig="editorConfig"
                        :mode="mode"
                        @onChange="handleChange"
                        @onCreated="handleCreated"

                    />
                  </div>

                </div>
              </a-form-item>

            </a-form>
          </a-col>
        </a-row>
        <a-drawer width="900" placement="right" :closable="false" :visible="drawerVisible" @close="onDrawerClose">
          <div class="wangeditor" :innerHTML="previewHtml"></div>
        </a-drawer>
      </a-layout-content>
    </a-layout>

<!--    <a-modal v-model:open="open"
             title="分级"
             @ok="handleModelOk">


    </a-modal>-->
  </div>


</template>

<script lang="ts">
import {createVNode, defineComponent, onBeforeUnmount, onMounted, ref, shallowRef} from 'vue';
import axios from 'axios';
import {message, Modal} from 'ant-design-vue';
import {Tool} from "@/util/tool";
import {useRoute} from "vue-router";
import {ExclamationCircleOutlined} from "@ant-design/icons-vue";
import '@wangeditor/editor/dist/css/style.css'
import { Editor, Toolbar } from '@wangeditor/editor-for-vue'
import {createEditor} from "@wangeditor/editor";
export default defineComponent({













// 编辑器实例，必须用 shallowRef

  components: { Editor, Toolbar },
  name: 'AdminCategory',
  setup() {
    // 富文本的预览
    const drawerVisible = ref(false)
    const previewHtml = ref();
    const handlePreviewContent= (editor:any)=>{
      const html = valueHtml.value;
      previewHtml.value = html;
      drawerVisible.value = true;
    }
    const onDrawerClose = ()=>{
      drawerVisible.value = false;
    }
// 编辑器实例，必须用 shallowRef
    const editorRef = shallowRef()
    // const editor = editorRef.value;
    // 内容 HTML
    const valueHtml = ref()

    // 模拟 ajax 异步获取内容
/*    onMounted(() => {
      setTimeout(() => {
        valueHtml.value = '<p>模拟 Ajax 异步设置内容</p>'
      }, 1500)
    })*/

    const toolbarConfig = {}
    const editorConfig = { placeholder: '请输入内容...' }

    // 组件销毁时，也及时销毁编辑器
    onBeforeUnmount(() => {

      const editor = editorRef.value
      if (editor == null) return
      editor.destroy()
    })

    const handleCreated = (editor:any) => {

      editorRef.value = editor // 记录 editor 实例，重要！
    }

    const content = ref();

    const handleChange = (editor:any) => {
      content.value = editor.getHtml()
      console.log('change:', editor.getHtml());
      console.log('content:', content.value);
    };





    const docs = ref();
    const route = useRoute()
    console.log("路由", route);
    console.log("route.path", route.path);
    console.log("route.query", route.query);
    console.log("route.param", route.params);
    console.log("route.fullPath", route.fullPath);
    console.log("route.name", route.name);
    console.log("route.meta", route.meta);
    const columns = [
      {
        title: '名称',
        dataIndex: 'name',
        slots: {customRender: 'name'}
      },

/*      {
        title: '名称',
        dataIndex: 'name'
      },*/
/*      {
        title: '父文档',
        key: 'parent',
        dataIndex: 'parent'
      },
      {
        title: '顺序',
        key: 'sort',
        dataIndex: 'sort'
      },*/
      {
        title: 'Action',
        key: 'action',
        slots: { customRender: 'action' }
      }
    ];
    const showConfirm = (id :number) => {
      Modal.confirm({
        title: 'Do you Want to delete these items?',
        icon: createVNode(ExclamationCircleOutlined),
        content: createVNode('div', { style: 'color:red;' }, '删除后不能恢复，确定要删除？'),
        onOk() {
          handleDelete(id);
        },
        onCancel() {
          console.log('Cancel');
        },
        class: 'test',
      });
    };
    /**
     * 表单
      */

    const loading = ref(false);

    // const queryName = ref();
    const doc = ref()
    doc.value = {
      ebookId : route.query.ebookId
    };
    const param = ref()
    param.value = {};
    //因为树选择组件的属性状态，会随当前编辑的节点变换而变化，所以单独声明一个响应式变量
    const treeSelectData = ref();
    treeSelectData.value = []
    const open = ref<boolean>(false);
    const modalLoading = ref(false);
    // const editor = createEditor();
    const handleSave = (e: MouseEvent) => {
      console.log(e);
      // doc.value.content = editor.getHtml()
      doc.value.content = content.value
      // valueHtml
      // const html = editor.getHtml();
       console.log("(((((((((((((((()))))))))))))))))))))))))))))", content.value);
      level1.value=[]
      axios.post("/doc/save", doc.value
        ).then((response) => {
        console.log(")))))))))))))", doc.value.content);
        modalLoading.value = false;
        const data = response.data;
        if (data.success) {
          open.value=false;
          modalLoading.value = true;
          handleQuery()
          message.success("保存成功")
        }else {
          message.error(data.message);
        }
      });

    }

    let ids: Array<string> = [];

    /**
     * 将某节点及其子孙节点全部存入一个数组中删除
     */
    const deleteIds = (level1: any, id: any) => {
      // console.log(treeSelectData, id);
      // 遍历数组，即遍历某一层节点
      for (let i = 0; i < level1.length; i++) {
        const node = level1[i];
        if (node.id === id) {
          // 如果当前节点就是目标节点
          ids.push(node.id)
          // 遍历所有子节点，将所有子节点全部都加入
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              deleteIds(children, children[j].id)
            }
          }
        } else {
          // 如果当前节点不是目标节点，则到其子节点再找找看。
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            deleteIds(children, id);
          }
        }
      }
    };

    /**
     * 将某节点及其子孙节点全部置为disabled
     */
    const setDisable = (treeSelectData: any, id: any) => {
      // console.log(treeSelectData, id);
      // 遍历数组，即遍历某一层节点
      for (let i = 0; i < treeSelectData.length; i++) {
        const node = treeSelectData[i];
        if (node.id === id) {
          // 如果当前节点就是目标节点
          console.log("disabled", node);
          // 将目标节点设置为disabled
          node.disabled = true;

          // 遍历所有子节点，将所有子节点全部都加上disabled
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            for (let j = 0; j < children.length; j++) {
              setDisable(children, children[j].id)
            }
          }
        } else {
          // 如果当前节点不是目标节点，则到其子节点再找找看。
          const children = node.children;
          if (Tool.isNotEmpty(children)) {
            setDisable(children, id);
          }
        }
      }
    };
    /**
     * 编辑
     */

    const edit = (record: any) => {
      doc.value = Tool.copy(record);
      handleQueryContent();
      open.value = true;
      // 不能选择当前节点及其所有子孙节点，作为父节点，会使树断开
      treeSelectData.value = Tool.copy(level1.value);
      //不能选择当前节点，或者子孙节点为父节点,会使用书断开
      setDisable(treeSelectData.value, record.id);


      //为选择树添加一个“无”
      treeSelectData.value.unshift({id:0, name:"无"})
    };
    /**
     * 新增
     * @param record
     */
    const add = () => {

      // open.value = true;
      doc.value = {
        ebookId : route.query.ebookId
      };


      treeSelectData.value = Tool.copy(level1.value) || [];
      //为选择树添加一个“无”
      treeSelectData.value.unshift({id:0, name:"无"})
      console.log("______________", treeSelectData.value)
    };
    /**
     * 数据查询
     **/

    /**
     * 树形结构 level1
     * [{
     * id: "",
     * name: "",
     * children:[{
     *   id: "",
     *   name: ""
     * }]
     *
     * }]
     */
    const level1 = ref(); // 一级文档树，children属性就是二级文档
    level1.value = [];

    const handleQuery = () => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      docs.value = [];
      axios.get("/doc/all/" + route.query.ebookId
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          docs.value = data.content;
          console.log("原始数组: ", docs.value)

          level1.value = []
          level1.value = Tool.array2Tree(docs.value, 0);
          console.log("树形数据是：", level1.value )
          // 父文档下拉框初始化，相当于点击新增
          treeSelectData.value = Tool.copy(level1.value) ;
          // 为选择树添加一个"无"
          treeSelectData.value.unshift({id: 0, name: '无'});
        } else {
          message.error(data.message);
        }
      });
    };




    const handleQueryContent = () => {
      loading.value = true;

      axios.get("/doc/find/"
          + doc.value.id
      ).then((response) => {
        loading.value = false;
        const data = response.data;
        if (data.success) {
          valueHtml.value=data.content;
        } else {
          message.error(data.message);
        }
      });
    };



    /**
     * 删除
     * @param id
     */
    const handleDelete = ( id: number)=>{
      loading.value = true;

      deleteIds(level1.value,id)
      console.log(id);
      axios.delete("/doc/delete/" + ids.join(",")
      ).then((response) => {
        const data = response.data;
        if (data.success) {

          handleQuery()
        }
      });
    }


  onMounted(()=>{
    handleQuery()
  });
    return {

      param,
      // categorys,
      columns,
      loading,
      doc,
      level1,
      editorRef,
      valueHtml,

      toolbarConfig,
      editorConfig,
      drawerVisible,
      previewHtml,
      modalLoading,
      treeSelectData,
      edit,
      add,
      handleCreated,

      handleSave,
      handlePreviewContent,
      handleQuery,
      handleDelete,
      handleQueryContent,
      showConfirm,
      onDrawerClose,

      handleChange,
      open
    }
  }
});
</script>

<style scoped>
img {
  width: 50px;
  height: 50px;
}



</style>

