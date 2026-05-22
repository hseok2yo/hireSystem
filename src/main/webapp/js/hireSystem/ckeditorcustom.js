
// 커스텀 업로드 어댑터
class MyUploadAdapter {
	constructor(loader) {
		this.loader = loader;
	}
	upload() {
		return this.loader.file.then(file => new Promise((resolve, reject) => {
			const formData = new FormData();
			formData.append('upload', file);
			fetch('/ckEditor/upload/image.do', {
				method: 'POST',
				body: formData
			})
				.then(res => res.json())
				.then(data => resolve({ default: data.url }))
				.catch(reject);
		}));
	}
}



function MyUploadAdapterPlugin(editor) {
	editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
		return new MyUploadAdapter(loader);
	};
}

const { ClassicEditor, Essentials, Bold, Italic, Font,
	Paragraph, Heading, Image, ImageUpload,
	Link, List,Underline} = CKEDITOR;

let editorInstance;

ClassicEditor
	.create(document.querySelector('#editor'), {
		licenseKey: 'eyJhbGciOiJFUzI1NiJ9.eyJleHAiOjE4MTAyNTI3OTksImp0aSI6IjkyODAyNDJhLTkzMDgtNGY3Ni05ZmQ3LThhZDQwZGRiMzc4MCIsImxpY2Vuc2VkSG9zdHMiOlsiMTI3LjAuMC4xIiwibG9jYWxob3N0IiwiMTkyLjE2OC4qLioiLCIxMC4qLiouKiIsIjE3Mi4qLiouKiIsIioudGVzdCIsIioubG9jYWxob3N0IiwiKi5sb2NhbCJdLCJ1c2FnZUVuZHBvaW50IjoiaHR0cHM6Ly9wcm94eS1ldmVudC5ja2VkaXRvci5jb20iLCJkaXN0cmlidXRpb25DaGFubmVsIjpbImNsb3VkIiwiZHJ1cGFsIl0sImxpY2Vuc2VUeXBlIjoiZGV2ZWxvcG1lbnQiLCJmZWF0dXJlcyI6WyJEUlVQIiwiRTJQIiwiRTJXIl0sInJlbW92ZUZlYXR1cmVzIjpbIlBCIiwiUkYiLCJTQ0giLCJUQ1AiLCJUTCIsIlRDUiIsIklSIiwiU1VBIiwiQjY0QSIsIkxQIiwiSEUiLCJSRUQiLCJQRk8iLCJXQyIsIkZBUiIsIkJLTSIsIkZQSCIsIk1SRSJdLCJ2YyI6ImQyZjc5NDM3In0.yJxAPmq39RbqMukzPo0HERlqvb9O3iTIrVZI625k6PiwZBxCBOTIcU4118v3QUhh3VHbymazgniMhsnLonlhPg',  // STEP 1에서 발급한 키
		extraPlugins: [MyUploadAdapterPlugin],
		plugins: [
			Essentials, Bold, Italic, Font,
			Paragraph, Heading, Image,
			ImageUpload,
			Link, List,Underline
		],
		toolbar: [
			'undo', 'redo', '|',
			'heading', '|',
			'bold', 'italic', '|',
			'fontSize', 'fontColor', '|',
			'link', 'bulletedList', 'numberedList', '|',
			'uploadImage','underline'
		],
		language: 'ko'
	})
	.then(editor => {
		editorInstance = editor;
	})
	.catch(error => {
		console.error(error);
	});
