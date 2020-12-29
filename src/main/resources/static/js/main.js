
var messageApi = Vue.resource('/message{/id}');

function getIndex(list, id){
    for(var i = 0;i<list.length;i++){
    if(list[i].id === id){
        return i;
    }else {
        return -1;
    }
}
}

Vue.component("message-form", {
    props:["messages", "messageAttr"],
    data: function(){
        return{
            text: '',
            id: ''
        }

    },
    watch: {
        messageAttr: function (newVal,oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
        '<input type="text" placeholder="Write something" v-model="text"/> ' +
        '<input type="button" value="Save" @click="save" />' +
        '' +
        '</div>',
    methods: {
        save: function () {
            var message = { text:this.text};

            if(this.id){
                messageApi.update({id:this.id}, message).then(rezult =>
                rezult.json().then(data => {
                    var index = getIndex(this.messages,data.id);
                    this.messages.splice(index, 1 ,data);
                    this.text = '';
                    this.id = '';
                })
                )
            }else {
                messageApi.save({}, message).then(rezult =>
                    rezult.json().then(data => {
                            this.messages.push(data);
                            this.text = '';
                        }
                    )
                );
            }
        }
    }
});

Vue.component('message-row', {
    props:["message","editMessage", "messages"],
    template: '<div>' +
        '<i>({{message.id}})</i>{{message.text}}' +
        '<span style="position: absolute; right: 0;">' +
        '<input type="button" value="Edit" @click="edit"/>' +
        '<input type="button" value="Delete" @click="del"/>' +
        '</span>' +
        '</div>',
    methods: {
        edit: function () {
            this.editMessage(this.message);
        },
        del: function () {
            concole.log(this.message.id);
            messageApi.remove({id:this.message.id}).then(rezult=>{
                if(rezult.ok){
                    this.messages.splice(this.messages.indexOf(this.message),1);
                }
            })
        }

    }
});

Vue.component('messages-list', {
    props:["messages"],
    data: function(){
        return{ message: null
        }
    },
    template: '<div style="position: relative; width: 300px;" >' +
        '<message-form :messages = "messages" :messageAttr = "message"/>' +
        '<message-row v-for="message in messages"  :key="message.id" :message="message" :editMessage ="editMessage"' +
        ':messages = "messages"' +
        '/></div>',
    methods:{
        editMessage: function (message) {
            this.message = message;
        }
    }

});

var app = new Vue({
    el: '#app',
    template: '<div>' +
        '<div v-if="!profile">Необхідно авторизуватись через <a href="/login">Google</a></div>' +
            '<div v-if="profile"">' +
                '<div>{{profile.name}} <a href="/logout">Вийти</a></div>' +
                '<messages-list :messages="messages"/>' +
            '</div>' +
        '</div>',
    data: {
        messages: frontEndData.messages,
        profile:frontEndData.profile
    },
    created: function () {
 //       messageApi.get().then(result=>
  //          result.json().then(date =>
    //            date.forEach(message => this.messages.push(message))
      //      )
        //)
    }
});