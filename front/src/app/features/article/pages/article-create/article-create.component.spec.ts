import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateArticleComponent } from './article-create.component';

describe('CreateArticleComponent', () => {
  let component: CreateArticleComponent;
  let fixture: ComponentFixture<CreateArticleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateArticleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateArticleComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
