<template>
  <a-layout>
      <a-layout-sider width="200" style="background: #fff">
      <a-menu
          mode="inline"
          :style="{ height: '100%', borderRight: 0 }"
          @click="handleClick"
      >
        <a-menu-item key="welcome">
          <router-link to="/" >
            <MailOutlined>
              <span>
                欢迎
              </span>
            </MailOutlined>
          </router-link>

        </a-menu-item>
        <a-sub-menu v-for="item in level1"
                    :key="item.id"

        >
          <template  v-slot:title>
                <span>
                  <user-outlined />
                    {{item.name}}
                </span>
          </template>
          <a-menu-item v-for="subItem in item.children"
                       :key="subItem.subItem"

          >{{subItem.name}}
          </a-menu-item>

        </a-sub-menu>
        <a-menu-item key="tip" :disabled="true">
          <span>以上菜单在分类管理配置</span>
        </a-menu-item>
      </a-menu>
      </a-layout-sider>
      <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '280px' }"
      >
        <a-list item-layout="vertical" size="large" grid="{ gutter: 16, column: 4 }"  :data-source="ebooks">
          <template #renderItem="{ item }">
            <a-list-item key="item.name">
              <template #actions>
                  <span v-for="{ icon, text } in actions" :key="icon">
                    <component :is="icon" style="margin-right: 8px" />
                    {{ text }}
                  </span>
              </template>

              <a-list-item-meta :description="item.description">
                <template #title>
                  <a :href="item.href">{{ item.name }}</a>
                </template>
                <template #avatar><a-avatar :src="item.cover" /></template>
              </a-list-item-meta>
            </a-list-item>
          </template>
        </a-list>
      </a-layout-content>
  </a-layout>
</template>



<script lang="ts">
import {defineComponent, onMounted, reactive, ref, toRef} from 'vue';
import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src
import axios from "axios";
import {LikeOutlined, MessageOutlined, StarOutlined} from "@ant-design/icons-vue";
import {Tool} from "@/util/tool";
import {message} from "ant-design-vue";


const listData: any = [];

for (let i = 0; i < 23; i++) {
  listData.push({
    href: 'https://www.antdv.com/',
    title: `ant design vue part ${i}`,
    avatar: 'https://joeschmoe.io/api/v1/random',
    description:
        'Ant Design, a design language for background applications, is refined by Ant UED Team.',
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
  });
}


export default defineComponent({
  name: 'HomeView',
  setup(){
    console.log("setup");
    const ebooks = ref();
    const actions = [
      { icon: StarOutlined, text: '156' },
      { icon: LikeOutlined, text: '156' },
      { icon: MessageOutlined, text: '2' }
    ];
    const ebookList = reactive({books:[]});
    onMounted(()=>{
      axios.get("/ebook/all").then((response)=>{
        const  data = response.data;
        ebooks.value = data.content;
        ebookList.books = data.content;
        // console.log(response);
      })
    })
    
    const handleClick = () => {
      console.log("111")
    }

    const level1 = ref()
    level1.value = []
    const categoryIds = ref();
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
    let categorys :any;
    const handleCategory = () => {

      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据

      axios.get("/category/all"
      ).then((response) => {

        const data = response.data;
        if (data.success) {
          categorys = data.content;
          console.log("原始数组: ", categorys)

          level1.value = []
          level1.value = Tool.array2Tree(categorys, 0);
          console.log("树形数据是：", level1 )
          // 加载完分类后，再加载电子书，否则如果分类树加载很慢，则电子书渲染会报错
        } else {
          message.error(data.message);
        }
      });
    }
    onMounted(() => {
      handleCategory();
      // handleQueryEbook();
    });
    return{
      ebooks,
      listData,
      level1,
      actions,
      handleClick,

    books : toRef(ebookList, "books"),
      pagination :{
        onChange: (page: number) => {
          console.log(page);
        },
        pageSize: 3,
      }

    }

  }
});
</script>

<style scoped>

.ant-avatar {
  width: 50px;
  height: 50px;
  line-height: 50px;
  border-radius: 8%;
  margin: 5px;

}
</style>
